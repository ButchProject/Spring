package com.spring.butch.entity;

import com.spring.butch.dto.PostDTO;
import jdk.jfr.Unsigned;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Table(name = "post_table")
public class PostEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // auto_increment
    private Long postId;
    @Column (unique = true) // unique 제약조건 추가
    private String postTitle; // 게시글 제목
    @Column
    private String postWhere; // 게시글 세부 지역
    @Column
    private String postDetail; // 게시글 내용
    @Column
    private Long postCapacityStudent; // 전체 학생수
    @Column
    private Long postCurrentStudent; // 현재 학생수
    @Column
    private Long postMoney; // 금액
    @Column
    private Long postSaleMoney; // 할인금액
    @Column
    private String postDay; // 요일
    @Column
    private Boolean recruitmentDone; // 모집완료

    // memberDTO객체를 memberEntity로 바꾸는 함수를 만들어야 함
    public static PostEntity toPostEntity(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();
        postEntity.setPostTitle(postDTO.getPostTitle());
        postEntity.setPostWhere(postDTO.getPostWhere());
        postEntity.setPostDetail(postDTO.getPostDetail());
        postEntity.setPostCapacityStudent(postDTO.getPostCapacityStudent());
        postEntity.setPostCurrentStudent(postDTO.getPostCurrentStudent());
        postEntity.setPostMoney(postDTO.getPostMoney());
        postEntity.setPostSaleMoney(postDTO.getPostSaleMoney());
        postEntity.setPostDay(postDTO.getPostDay());
        postEntity.setRecruitmentDone(postDTO.getRecruitmentDone());

        return postEntity;
    }

}
