package com.spring.butch.api.board.dto;

import com.spring.butch.api.board.entity.BoardEntity;
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
    private String boardWriter; // 게시글 작성자
    private String boardState; // 게시글 지역(도)
    private String boardCity; // 게시글 지역(시)
    private String boardWhere; // 게시글 지역(구)
    private String boardDetail; // 게시글 내용
    private Integer boardCurrentStudents; // 현재 학생수

    // Entity에 없는 내용
    private Integer boardBus45 = 0;
    private Integer boardBus25 = 0;
    private Integer boardBus12 = 0;
    private Integer boardBusSumMoney; // 총 버스 총합 금액
    private Integer boardSoloMoney; // 혼자 버스 대여시 금액
    private Integer boardBusSaleMoney; // 내가 부담해야 할 금액

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setBoardId(boardEntity.getBoardId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardState(boardEntity.getBoardState());
        boardDTO.setBoardCity(boardEntity.getBoardCity());
        boardDTO.setBoardWhere(boardEntity.getBoardWhere());
        boardDTO.setBoardDetail(boardEntity.getBoardDetail());
        boardDTO.setBoardCurrentStudents(boardEntity.getBoardCurrentStudents());

        return boardDTO;
    }

    public static BoardDTO toBoardDTODetail(BoardEntity boardEntity, Integer students) {
        BoardDTO boardDTO = new BoardDTO();
        Integer temp = students;

        boardDTO.setBoardId(boardEntity.getBoardId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardState(boardEntity.getBoardState());
        boardDTO.setBoardCity(boardEntity.getBoardCity());
        boardDTO.setBoardWhere(boardEntity.getBoardWhere());
        boardDTO.setBoardDetail(boardEntity.getBoardDetail());
        boardDTO.setBoardCurrentStudents(boardEntity.getBoardCurrentStudents());

        boardDTO.BusCount(students); // 내가 가진 학생수로 '혼자' 대여해야 할 버스비를 보여줌.
        boardDTO.setBoardSoloMoney(boardDTO.getBoardBus45(), boardDTO.getBoardBus25(), boardDTO.getBoardBus12());
        boardDTO.setBoardBus45(0); boardDTO.setBoardBus25(0); boardDTO.setBoardBus12(0);

        boardDTO.BusCount(temp + boardEntity.getBoardCurrentStudents()); // 45, 25, 12인승 버스의 각 수를 set함.
        boardDTO.setBoardBusSumMoney(boardDTO.getBoardBus45(), boardDTO.getBoardBus25(), boardDTO.getBoardBus12());
        boardDTO.setBoardBusSaleMoney(students); // 내가 지불 해야하는 버스의 금액

        return boardDTO;
    }
    public void setBoardBusSumMoney(Integer bus1, Integer bus2, Integer bus3) {
        this.boardBusSumMoney = (bus1 * 5000000) + (bus2 * 3500000) + (bus3 * 2400000);
    }

    public void setBoardSoloMoney(Integer bus1, Integer bus2, Integer bus3) {
        this.boardSoloMoney = (bus1 * 5000000) + (bus2 * 3500000) + (bus3 * 2400000);
    }

    public void setBoardBusSaleMoney(Integer allStudents) {
        Double ratio = (double) allStudents / (allStudents + getBoardCurrentStudents());

        this.boardBusSaleMoney = (int) ((Integer) getBoardBusSumMoney() * ratio);
    }

    public void BusCount(Integer allStudents) { // 학생 수에 따른, 효율적인 버스 대여를 추천해줌.
        while (true) {
            if(allStudents == 0) // 나누어 떨어지는 경우
                break;
            if (allStudents >= 45) {
                setBoardBus45(allStudents / 45);
                allStudents %= 45;
            } else if (allStudents < 45 && allStudents >= 25) {
                if (45 - allStudents < 20) {
                    setBoardBus45(getBoardBus45() + 1);
                    break;
                }
                else {
                    setBoardBus25(allStudents / 25);
                    allStudents %= 25;
                }
            } else {
                if (25 - allStudents < 13) {
                    setBoardBus25(getBoardBus25() + 1);
                    break;
                }
                else {
                    setBoardBus12(getBoardBus12() + 1);
                    break;
                }
            }
        }
    }

}
