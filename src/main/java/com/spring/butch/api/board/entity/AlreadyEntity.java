package com.spring.butch.api.board.entity;

import com.spring.butch.api.board.dto.AlreadyDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "already_table")
public class AlreadyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alreadyId;

    @Column
    private Long postId;

    @Column
    private String email;

    public static AlreadyDTO toAlreadyDTO(AlreadyEntity alreadyEntity) {
        return AlreadyDTO.builder()
                .alreadyId(alreadyEntity.getAlreadyId())
                .postId(alreadyEntity.getPostId())
                .email(alreadyEntity.getEmail())
                .build();
    }

}
