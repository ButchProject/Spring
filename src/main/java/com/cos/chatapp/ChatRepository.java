package com.cos.chatapp;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable // 커서를 닫지않고 계속 유지 (mongoDB에서 데이터가 추가될 때마다 새로운 데이터를 계속 스트리밍)
    @Query("{sender: ?0, receiver: ?1}")
    Flux<Chat> mFindBySender(String sender, String receiver);
    // Flux : 데이터의 흐름? -> response를 유지하면서 데이터를 계속 흘려보내기

    @Tailable // 채팅방 번호로 찾아서 채팅 (1:N 채팅)
    @Query("{roomNum: ?0}")
    Flux<Chat> mFindByRoomNum(Integer roomNum);

    @Query("{sender: ?0}")
    Flux<Chat> findBySender(String sender);
}
