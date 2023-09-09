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
    private Integer postBus45; // 45인승 버스
    private Integer postBus25; // 25인승 버스
    private Integer postBus12; // 12인승 버스
    private Integer postCurrentStudent; // 현재 학생수

    // Entity에 없는 내용
    private Integer postBusSumMoney; // 대여버스 총합 금액
    private Integer postBusSaleMoney; // 내가 가진 학생에 대한 금액

    public static PostDTO toPostDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();

        postDTO.setPostId(postEntity.getPostId());
        postDTO.setPostTitle(postEntity.getPostTitle());
        postDTO.setPostWhere(postEntity.getPostWhere());
        postDTO.setPostDetail(postEntity.getPostDetail());
        postDTO.setPostBus45(postEntity.getPostBus45());
        postDTO.setPostBus25(postEntity.getPostBus25());
        postDTO.setPostBus12(postDTO.getPostBus12());
        postDTO.setPostCurrentStudent(postEntity.getPostCurrentStudent());

        return postDTO;
    }

    public void setPostBusSumMoney() {
        this.postBusSumMoney = (getPostBus45() * 5000000) + (getPostBus25() * 3500000) + (getPostBus12() * 2400000);
    }

    public void setPostBusSaleMoney() {
        this.postBusSaleMoney = getPostBusSumMoney() * ()
    }

}
