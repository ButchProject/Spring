package com.spring.butch.api.post.dto;

import com.spring.butch.api.post.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDTO {
    private Long postId;
    private String postTitle; // 게시글 제목
    private String postWhere; // 게시글 상세위치
    private String postDetail; // 게시글 내용
    private Long postCapacityStudent; // 전체 학생수
    private Long postCurrentStudent; // 현재 학생수
    private Long postMoney; // 기존 금액
    private Long postSaleMoney; // 할인 금액
    private String postDay; // 요일
    private Boolean recruitmentDone; // 모집완료

    public static PostDTO toPostDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();

        postDTO.setPostId(postEntity.getPostId());
        postDTO.setPostTitle(postEntity.getPostTitle());
        postDTO.setPostWhere(postEntity.getPostWhere());
        postDTO.setPostDetail(postEntity.getPostDetail());
        postDTO.setPostCapacityStudent(postEntity.getPostCapacityStudent());
        postDTO.setPostCurrentStudent(postEntity.getPostCurrentStudent());
        postDTO.setPostMoney(postEntity.getPostMoney());
        postDTO.setPostSaleMoney(postEntity.getPostSaleMoney());
        postDTO.setPostDay(postEntity.getPostDay());
        postDTO.setRecruitmentDone(postEntity.getRecruitmentDone());

        return postDTO;
    }
}
