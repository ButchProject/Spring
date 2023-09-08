package com.cos.chatapp.controller;

import com.cos.chatapp.Chat;
import com.cos.chatapp.ChatRepository;
import com.cos.chatapp.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.awt.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatRepository chatRepository;


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

    @CrossOrigin
    @PostMapping("/chat/list")
    public Flux<ChatRoom> getChatRoomsBySender(@RequestBody String sender) {
        return chatRepository.findBySender(sender)
                .map(chat -> new ChatRoom(chat.getSender(), chat.getRoomNum()))
                .distinct();
    }
}
