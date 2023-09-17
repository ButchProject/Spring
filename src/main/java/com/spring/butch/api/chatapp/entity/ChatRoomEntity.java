package com.spring.butch.api.chatapp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chatRoom")
public class ChatRoomEntity {
    @Id
    private String user1;
    private String user2;
    private Integer roomNum;
}
