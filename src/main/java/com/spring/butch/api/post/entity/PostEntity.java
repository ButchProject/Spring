package com.spring.butch.api.post.entity;

import com.spring.butch.api.post.dto.PostDTO;
import lombok.Getter;
import lombok.Setter;

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
    private Integer postBus45; // 45인승 버스
    @Column
    private Integer postBus25; // 25인승 버스
    @Column
    private Integer postBus12; // 12인승 버스
    @Column
    private Integer postCurrentStudent; // 현재 학생수


    // memberDTO객체를 memberEntity로 바꾸는 함수를 만들어야 함
    public static PostEntity toPostEntity(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();

        postEntity.setPostTitle(postDTO.getPostTitle());
        postEntity.setPostWhere(postDTO.getPostWhere());
        postEntity.setPostDetail(postDTO.getPostDetail());
        postEntity.setPostBus45(postDTO.getPostBus45());
        postEntity.setPostBus25(postDTO.getPostBus25());
        postEntity.setPostBus12(postDTO.getPostBus12());
        postEntity.setPostCurrentStudent(postDTO.getPostCurrentStudent());


        return postEntity;
    }

}
