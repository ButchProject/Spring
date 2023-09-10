package com.spring.butch.api.post.entity;

import com.spring.butch.api.post.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
@Table(name = "post_table")
public class BoardEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) // auto_increment
    private Long boardId;
    @Column (unique = true) // unique 제약조건 추가
    private String boardTitle; // 게시글 제목
    @Column
    private String boardState; // 게시글 지역(도)
    @Column
    private String boardCity; // 게시글 지역(시)
    @Column
    private String boardWhere; // 게시글 지역(구)
    @Column
    private String boardDetail; // 게시글 내용
    @Column
    private Integer boardCurrentMoney; // 기존 금액
    @Column
    private Integer boardSaleMoney; // 현재 금액

    // memberDTO객체를 memberEntity로 바꾸는 함수를 만들어야 함
    public static BoardEntity toBoardEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardState(boardDTO.getBoardState());
        boardEntity.setBoardCity(boardDTO.getBoardCity());
        boardEntity.setBoardWhere(boardDTO.getBoardWhere());
        boardEntity.setBoardDetail(boardDTO.getBoardDetail());
        boardEntity.setBoardCurrentMoney(boardDTO.getBoardCurrentMoney());
        boardEntity.setBoardSaleMoney(boardDTO.getBoardSaleMoney());


        return boardEntity;
    }

}
