package com.spring.butch.api.chatapp.controller;

import com.spring.butch.api.chatapp.entity.Chat;
import com.spring.butch.api.chatapp.repository.ChatRepository;
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
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ChatController {
    private final ChatRepository chatRepository;
    private final SecurityService securityService;

    @CrossOrigin
    @GetMapping("/chat/list")
    public Flux<Map<String, Integer>> getChatRoomNumbersByUser(HttpServletRequest request) {
        // 채팅 목록 보기
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        String memberEmail = securityService.getSubject(token);

        Flux<Chat> senderChats = chatRepository.findBySender(memberEmail);
        Flux<Chat> receiverChats = chatRepository.findByReceiver(memberEmail);

        return Flux.merge(senderChats, receiverChats)
                .map(Chat::getRoomNum)
                .distinct()
                .map(roomNumber -> Collections.singletonMap("roomNumber", roomNumber));
    }


    // 1:1 채팅에서 사용
    @CrossOrigin
    @GetMapping(value = "sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMessage(@PathVariable String sender, @PathVariable String receiver){
        return chatRepository.mFindBySender(sender, receiver)
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
