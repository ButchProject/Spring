package com.spring.butch.api.chatapp.repository;

import com.spring.butch.api.chatapp.entity.Chat;
import com.spring.butch.api.chatapp.entity.ChatRoomEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoomEntity, String> {

    Mono<ChatRoomEntity> findTopByOrderByRoomNumDesc();
    @Query("{ $or: [ { 'user1': ?0, 'user2': ?1 }, { 'user1': ?1, 'user2': ?0 } ] }")
    Mono<ChatRoomEntity> findByUsers(String user1, String user2);

    Flux<Chat> findByUser1(String user1);

    Flux<Chat> findByUser2(String user2);
}