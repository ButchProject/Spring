package com.spring.butch.api.board.dto;

import com.spring.butch.api.board.entity.AlreadyEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlreadyDTO {
    private Long alreadyId;

    private Long postId;

    private String email;

    public static AlreadyEntity toAlreadyEntity(AlreadyDTO alreadyDTO) {
        return AlreadyEntity.builder()
                .alreadyId(alreadyDTO.getAlreadyId())
                .postId(alreadyDTO.getPostId())
                .email(alreadyDTO.getEmail())
                .build();
    }
}
