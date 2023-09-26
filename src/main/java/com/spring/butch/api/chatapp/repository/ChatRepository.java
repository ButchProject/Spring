package com.spring.butch.api.chatapp.repository;

import com.spring.butch.api.chatapp.entity.Chat;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    // Flux : 데이터의 흐름? -> response를 유지하면서 데이터를 계속 흘려보내기

    @Tailable // 커서를 닫지않고 계속 유지 (mongoDB에서 데이터가 추가될 때마다 새로운 데이터를 계속 스트리밍)
    // 채팅방 번호로 찾아서 채팅 (1:N 채팅)
    @Query("{roomNum: ?0}")
    Flux<Chat> mFindByRoomNum(Integer roomNum);


}
