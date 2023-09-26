package com.spring.butch.api.chatapp.controller;


import com.spring.butch.api.chatapp.dto.ChatRoomDTO;
import com.spring.butch.api.chatapp.entity.Chat;
import com.spring.butch.api.chatapp.entity.ChatRoomEntity;
import com.spring.butch.api.chatapp.repository.ChatRepository;
import com.spring.butch.api.chatapp.repository.ChatRoomRepository;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.service.MemberService;

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

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ChatController {
    private final ChatRepository chatRepository;
    private final SecurityService securityService;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    @CrossOrigin
    @PostMapping("/chat/createRoom")
    public Mono<ChatRoomDTO> createChatRoom(HttpServletRequest request, @RequestBody ChatRoomEntity chatRoomEntity) {

        // 토큰 검증
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        // user1 설정 (user2는 Request Body 들어옴)
        String memberEmail = claims.getSubject();


        chatRoomEntity.setUser1(memberEmail);

        // user1 학원 이름 설정
        MemberDTO member = memberService.findByEmail(memberEmail);
        String user1AcademyName = member.getAcademyName();
        chatRoomEntity.setUser1Academy(user1AcademyName);

        return chatRoomRepository.findByUsers(chatRoomEntity.getUser1(), chatRoomEntity.getUser2()) // user1과 user2로 이루어진 채팅방을 찾음
                .switchIfEmpty( // 없으면 새로운 채팅방을 만듦.
                        chatRoomRepository.findTopByOrderByRoomNumDesc()
                                .map(lastchatroom -> lastchatroom.getRoomNum() + 1)
                                .defaultIfEmpty(0) // lastroomNum이 없으면 0으로 초기화
                                .flatMap(roomNum -> {
                                    chatRoomEntity.setRoomNum(roomNum);
                                    return chatRoomRepository.save(chatRoomEntity);
                                })
                )
                .map(savedChatRoom -> {
                    String otherUserEmail = chatRoomEntity.getUser2();
                    String otherUserAcademyName = chatRoomEntity.getUser2Academy();

                    return new ChatRoomDTO(
                            savedChatRoom.getRoomNum(),
                            memberEmail,
                            otherUserEmail,
                            otherUserAcademyName
                    );
                });
    }


    @CrossOrigin
    @GetMapping("/chat/list") // 채팅 목록 보기
    public Flux<ChatRoomDTO> getChatRoomNumbersByUser(HttpServletRequest request) {
        // 토큰 검사
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        // 토큰에서 이메일 파싱
        String memberEmail = claims.getSubject();

        return Flux.concat(chatRoomRepository.findByUser1(memberEmail), chatRoomRepository.findByUser2(memberEmail))
                .map(chat -> {
                    String otherUserEmail;
                    String otherAcademyName;

                    if (memberEmail.equals(chat.getUser1())) {
                        otherUserEmail = chat.getUser2();
                        otherAcademyName = chat.getUser2Academy();
                    } else {
                        otherUserEmail = chat.getUser1();
                        otherAcademyName = chat.getUser1Academy();
                    }

                    return new ChatRoomDTO(
                            chat.getRoomNum(),
                            memberEmail,
                            otherUserEmail,
                            otherAcademyName
                    );
                })
                .distinct(ChatRoomDTO::getRoomNum);
    }


    // 1:1 채팅에서 사용
//    @CrossOrigin
//    @GetMapping(value = "sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Chat> getMessage(@PathVariable String sender, @PathVariable String receiver){
//        return chatRepository.mFindByUser1(sender, receiver)
//                .subscribeOn(Schedulers.boundedElastic());
//    }

    @CrossOrigin
    @GetMapping(value = "/chat/room", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(HttpServletRequest request, @RequestParam Integer roomNum){
        // 토큰 검사
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);
        System.out.println("roomNum = " + roomNum);
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