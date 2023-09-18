package com.spring.butch.api.post.domain;

import com.spring.butch.api.post.dto.NodeDTO;
import com.spring.butch.api.post.dto.BoardDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class BoardNodeDomain { // 가져다 사용하는 데이터 사용방식
        private BoardDTO boardDTO;
        private List<NodeDTO> nodeDTOList;

    public BoardNodeDomain(BoardDTO boardDTO, List<NodeDTO> nodeDTOList) {
        this.boardDTO = boardDTO;
        this.nodeDTOList = nodeDTOList;
    }
    // DB에 저장되어있는 방식이 post 구성 DB, node DB 두가지로 구성되어있음.
    // 이 데이터를 가져올 때, 하나는 DTO를, 다른 하나는 List<DTO>를 합쳐서 가져오도록 함
    // 이를 묶어서 한번에 보내주기 위해, 그리고 RequestBody를 하나만 사용할수 있기 때문에
    // 데이터를 합쳐서 전송 혹은 받아오기를 함.
}
