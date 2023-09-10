package com.spring.butch.api.post.dto;

import com.spring.butch.api.post.entity.BoardEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardDTO {
    private Long boardId;
    private String boardTitle; // 게시글 제목
    private String boardState; // 게시글 지역(도)
    private String boardCity; // 게시글 지역(시)
    private String boardWhere; // 게시글 지역(구)
    private String boardDetail; // 게시글 내용
    private Integer boardCurrentMoney; // 기존 금액
    private Integer boardSaleMoney; // 현재 금액

    // Entity에 없는 내용
//    private Integer postBusSumMoney; // 대여버스 총합 금액
//    private Integer postBusSaleMoney; // 내가 가진 학생에 대한 금액
//    private Integer postStudentSum; // 버스에 따른 전체 학생 수

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setBoardId(boardEntity.getBoardId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardState(boardEntity.getBoardState());
        boardDTO.setBoardCity(boardEntity.getBoardCity());
        boardDTO.setBoardWhere(boardEntity.getBoardWhere());
        boardDTO.setBoardDetail(boardEntity.getBoardDetail());
        boardDTO.setBoardCurrentMoney(boardEntity.getBoardCurrentMoney());
        boardDTO.setBoardSaleMoney(boardEntity.getBoardSaleMoney());

//        postDTO.setPostBusSumMoney(postEntity.getPostBus45(), postEntity.getPostBus25(), postEntity.getPostBus12());
//        // postDTO.setPostBusSaleMoney();// token 검사하고 email을 가지고, 그 회원의 numofstu를 알아오면 됨.
//        postDTO.setPostStudentSum(postEntity.getPostBus45(), postEntity.getPostBus25(), postEntity.getPostBus12());

        return boardDTO;
    }

//    public void setPostBusSumMoney(Integer bus1, Integer bus2, Integer bus3) {
//        this.postBusSumMoney = (bus1 * 5000000) + (bus2 * 3500000) + (bus3 * 2400000);
//    }
//
//    public void setPostBusSaleMoney(Integer students) {
//        this.postBusSaleMoney = getPostBusSumMoney() * (students / (students + getPostCurrentStudent()));
//    }
//
//    public void setPostStudentSum(Integer bus1, Integer bus2, Integer bus3) {
//        this.postStudentSum = (bus1 * 45) + (bus2 * 25) + (bus3 * 12);
//    }

}
