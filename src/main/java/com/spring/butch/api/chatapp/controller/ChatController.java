package com.spring.butch.api.chatapp.controller;

import com.spring.butch.api.chatapp.dto.ChatRoom;
import com.spring.butch.api.chatapp.entity.Chat;
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
    @PostMapping("/chat/createRoom")
    public Mono<ChatRoom> createChatRoom(HttpServletRequest request, @RequestBody ChatRoom chatRoom) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        String memberEmail = claims.getSubject();
        chatRoom.setUser1(memberEmail);


        return chatRoomRepository.findByUsers(chatRoom.getUser1(), chatRoom.getUser2())
                .switchIfEmpty(
                        chatRoomRepository.findTopByOrderByRoomNumDesc()
                                .map(lastchatRoom -> lastchatRoom.getRoomNum() + 1)
                                .defaultIfEmpty(0)
                                .flatMap(roomNum -> {
                                    chatRoom.setRoomNum(roomNum);
                                    return chatRoomRepository.save(chatRoom);
                                })
                );
    }

    @CrossOrigin
    @GetMapping("/chat/list")
    public Flux<Map<String, Object>> getChatRoomNumbersByUser(HttpServletRequest request) {
        // 채팅 목록 보기
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        String memberEmail = claims.getSubject();

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