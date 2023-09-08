package com.cos.chatapp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoom {
    private String sender;
    private Integer roomNum;
}