package com.spring.butch.api.chatapp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "chat")
public class Chat {
    @Id
    private String id;
    private String message; // 메세지 내용
    private String sender; // 보내는 사람
    private String receiver; // 받는 사람 (1 : 1 채팅에서 필요)
    private Integer roomNum; // 방 번호
    private LocalDateTime createdAt;


}
