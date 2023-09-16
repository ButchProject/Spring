package com.spring.butch.api.post.service;


import com.spring.butch.api.member.entity.MemberEntity;
import com.spring.butch.api.member.repository.MemberRepository;
import com.spring.butch.api.post.domain.BoardNodeDomain;
import com.spring.butch.api.post.dto.NodeDTO;
import com.spring.butch.api.post.dto.BoardDTO;
import com.spring.butch.api.post.entity.NodeEntity;
import com.spring.butch.api.post.entity.BoardEntity;
import com.spring.butch.api.post.repository.NodeRepository;
import com.spring.butch.api.post.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final NodeRepository nodeRepository;
    private final PlatformTransactionManager transactionManager;

    public List<BoardNodeDomain> boardNodeListAll() { // 게시글 전체 보기 (최신순으로)
        List<BoardEntity> boardEntityList = boardRepository.sortBoardListByDesc(); // 최신순으로 게시글 db가져오기
        List<BoardNodeDomain> listAll = new ArrayList<>();

        if (boardEntityList != null) {
            for(BoardEntity postEntity : boardEntityList) {
                BoardDTO boardDTO = BoardDTO.toBoardDTO(postEntity);
                List<NodeDTO> nodeDTOList = new ArrayList<>();
                List<NodeEntity> nodeEntities = nodeRepository.findSameBoardIdNode(postEntity.getBoardId());
                // 게시글에 대한 정류장 뽑아서 가져와주기

                for(NodeEntity nodeEntity : nodeEntities) {
                    nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
                }
                BoardNodeDomain boardNodeDomain = new BoardNodeDomain();

                boardNodeDomain.setBoardDTO(boardDTO);
                boardNodeDomain.setNodeDTOList(nodeDTOList);

                listAll.add(boardNodeDomain);
            }
            return listAll;
        }
        else {
            return null;
        }
    }
    public void boardSave(BoardDTO boardDTO, List<NodeDTO> nodeDTOList) { // 게시글 저장
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO);
        boardRepository.save(boardEntity);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeEntity.setSameBoardId(boardEntity.getBoardId()); // sameBoardId와 boardId를 연결함
            nodeRepository.save(nodeEntity);
        }
    }

    public BoardDTO detailBoard(Long id, String email) { // 게시판 상세보기 (정류장 빼고)
        Optional<BoardEntity> owner = boardRepository.findById(id);
        Optional<MemberEntity> owner2 = memberRepository.findByMemberEmail(email);
        if (owner.isPresent() && owner2.isPresent()) {
            Integer students = owner2.get().getNumberOfStudents();
            BoardDTO boardDTO = BoardDTO.toBoardDTODetail(owner.get(), students);
            return boardDTO;
        } else
            return null;
    }

    public List<NodeDTO> detailNode(Long id) { // 정류장 상세보기
        List<NodeEntity> nodeEntityList = nodeRepository.findSameBoardIdNode(id);
        List<NodeDTO> nodeDTOList = new ArrayList<>();
        if (!nodeEntityList.isEmpty()) {
            for (NodeEntity nodeEntity : nodeEntityList) {
                nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
            }
            return nodeDTOList;
        } else
            return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBoardNode(Long id, BoardDTO boardDTO, List<NodeDTO> nodeDTOList) { // 수정한 게시판 내용 저장하기.
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO);
        boardRepository.updateBoardEntity(id, boardEntity.getBoardTitle(),
                boardEntity.getBoardState(), boardEntity.getBoardCity(), boardEntity.getBoardWhere(),
                boardEntity.getBoardDetail(), boardEntity.getBoardCurrentStudents());
        nodeRepository.deleteNodeEntities(id);

        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }
    // DTO를 Entity 형식으로 수정하고, 각 데이터 하나하나를 직접 지정해서 Quary에 대입함

    @Transactional
    public void deleteBoardNode(Long id) { // 게시물 삭제하기
        boardRepository.deleteById(id);
        nodeRepository.deleteNodeEntities(id);
    }
    // 게시물에 대한 아이디 정보만 받음. 아이디에 일치되는 게시물 데이터 전부 삭제
    // 즉, postEntity에서는 데이터 행 하나만 삭제
    // nodeEntity에서는 여러 개의 행 삭제

    public List<BoardDTO> searchWantCity(String city) {
        List<BoardEntity> sameWhereList = boardRepository.findSameWhere(city);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity sameWhere : sameWhereList) {
            BoardDTO boardDTO = BoardDTO.toBoardDTO(sameWhere);
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }
}
