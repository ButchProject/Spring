package com.spring.butch.api.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoom {
    private String user1;
    private String user2;
    private Integer roomNum;
}