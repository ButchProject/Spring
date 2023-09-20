package com.spring.butch.api.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomDTO {
    private Integer roomNum;
    private String myEmail;
    private String otherUserEmail;
    private String otherUserAcademyName;
}