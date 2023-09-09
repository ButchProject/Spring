package com.spring.butch.api.post.dto;

import com.spring.butch.api.member.entity.MemberEntity;
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
    private Integer postStudentSum; // 버스에 따른 전체 학생 수

    public static PostDTO toPostDTO(PostEntity postEntity) {
        PostDTO postDTO = new PostDTO();

        postDTO.setPostId(postEntity.getPostId());
        postDTO.setPostTitle(postEntity.getPostTitle());
        postDTO.setPostWhere(postEntity.getPostWhere());
        postDTO.setPostDetail(postEntity.getPostDetail());
        postDTO.setPostBus45(postEntity.getPostBus45());
        postDTO.setPostBus25(postEntity.getPostBus25());
        postDTO.setPostBus12(postEntity.getPostBus12());
        postDTO.setPostCurrentStudent(postEntity.getPostCurrentStudent());

        postDTO.setPostBusSumMoney(postEntity.getPostBus45(), postEntity.getPostBus25(), postEntity.getPostBus12());
        // postDTO.setPostBusSaleMoney();// token 검사하고 email을 가지고, 그 회원의 numofstu를 알아오면 됨.
        postDTO.setPostStudentSum(postEntity.getPostBus45(), postEntity.getPostBus25(), postEntity.getPostBus12());

        return postDTO;
    }

    public void setPostBusSumMoney(Integer bus1, Integer bus2, Integer bus3) {
        this.postBusSumMoney = (bus1 * 5000000) + (bus2 * 3500000) + (bus3 * 2400000);
    }

    public void setPostBusSaleMoney(Integer students) {
        this.postBusSaleMoney = getPostBusSumMoney() * (students / (students + getPostCurrentStudent()));
    }

    public void setPostStudentSum(Integer bus1, Integer bus2, Integer bus3) {
        this.postStudentSum = (bus1 * 45) + (bus2 * 25) + (bus3 * 12);
    }

}
