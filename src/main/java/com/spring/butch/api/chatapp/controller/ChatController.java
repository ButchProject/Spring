package com.spring.butch.api.chatapp.controller;

import com.spring.butch.api.chatapp.entity.Chat;
import com.spring.butch.api.chatapp.entity.ChatRoomEntity;
import com.spring.butch.api.chatapp.repository.ChatRepository;
import com.spring.butch.api.chatapp.repository.ChatRoomRepository;
import com.spring.butch.api.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ChatController {
    private final ChatRepository chatRepository;
    private final SecurityService securityService;
    private final ChatRoomRepository chatRoomRepository;
    @CrossOrigin
    @PostMapping("/chat/createRoom/{id}")
    public Mono<ChatRoomEntity> createChatRoom(@PathVariable Long id, HttpServletRequest request, @RequestBody ChatRoomEntity chatRoomEntity) {
        // 토큰 검증
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        // user1 설정 (user2는 Request Body 들어옴)
        String memberEmail = claims.getSubject();


        chatRoomEntity.setUser1(memberEmail);

        // chatRoomEntity 구성에 맞게 저장하고 반환
        return chatRoomRepository.findByUsers(chatRoomEntity.getUser1(), chatRoomEntity.getUser2())
                .switchIfEmpty(
                        chatRoomRepository.findTopByOrderByRoomNumDesc()
                                .map(lastchatRoom -> lastchatRoom.getRoomNum() + 1)
                                .defaultIfEmpty(0) // lastRoom이 없으면 0으로 초기화
                                .flatMap(roomNum -> {
                                    chatRoomEntity.setRoomNum(roomNum);
                                    return chatRoomRepository.save(chatRoomEntity);
                                })
                );
    }

    @CrossOrigin
    @GetMapping("/chat/list") // 채팅 목록 보기
    public Flux<Map<String, Object>> getChatRoomNumbersByUser(HttpServletRequest request) {
        // 토큰 검사
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        // 토큰에서 이메일 파싱
        String memberEmail = claims.getSubject();

        // user1, user2 검색
        Flux<Chat> senderChats = chatRoomRepository.findByUser1(memberEmail);
        Flux<Chat> receiverChats = chatRoomRepository.findByUser2(memberEmail);


        return Flux.merge(senderChats, receiverChats)
                .filter(chat -> chat.getRoomNum() != null)
                .map(chat -> {
                    String otherUserEmail = memberEmail.equals(chat.getUser1()) ? chat.getUser2() : chat.getUser1();
                    Map<String, Object> roomInfo = new HashMap<>();
                    roomInfo.put("roomNumber", chat.getRoomNum());
                    roomInfo.put("otherUser", otherUserEmail);
                    return roomInfo;
                })
                .distinct(roomInfo -> (Integer) roomInfo.get("roomNumber"));
    }


    // 1:1 채팅에서 사용
    @CrossOrigin
    @GetMapping(value = "sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessage(@PathVariable String sender, @PathVariable String receiver){
        return chatRepository.mFindByUser1(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(@PathVariable Integer roomNum){
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat){
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat); // object를 리턴하면 자동으로 JSON으로 바꿔줌 (MessageConverter)
    }

}