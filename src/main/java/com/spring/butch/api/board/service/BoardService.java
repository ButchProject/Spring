package com.spring.butch.api.board.service;


import com.spring.butch.api.member.entity.MemberEntity;
import com.spring.butch.api.member.repository.MemberRepository;
import com.spring.butch.api.board.domain.BoardNodeDomain;
import com.spring.butch.api.board.dto.NodeDTO;
import com.spring.butch.api.board.dto.BoardDTO;
import com.spring.butch.api.board.entity.NodeEntity;
import com.spring.butch.api.board.entity.BoardEntity;
import com.spring.butch.api.board.repository.NodeRepository;
import com.spring.butch.api.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final NodeRepository nodeRepository;

    public List<BoardNodeDomain> boardNodeListAll() { // 게시글 전체 보기 (최신순으로)
        List<BoardEntity> boardEntityList = boardRepository.sortBoardListByDesc(); // 최신순으로 게시글 구성 db가져오기
        List<BoardNodeDomain> listAll = new ArrayList<>();
        // 게시글 1이라고 하면, 게시글에 대한 내용과 이에 대한 정류장이 있음
        // 이런 형태의 게시글 10개가 있다면 그 10개의 게시글을 리스트 안에 넣어서 묶는 과정

        if (boardEntityList != null) { //DB에서 가져온 내용이 없지 않으면 실행
            for(BoardEntity postEntity : boardEntityList) {
                BoardDTO boardDTO = BoardDTO.toBoardDTO(postEntity);
                List<NodeDTO> nodeDTOList = new ArrayList<>();
                List<NodeEntity> nodeEntities = nodeRepository.findSameBoardIdNode(postEntity.getBoardId());
                // 게시글에 대한 정류장 뽑아서 가져와주기
                // 가져온 게시글의 구성, 그 중 id를 가지고 정류장을 가져옴.

                for(NodeEntity nodeEntity : nodeEntities) {
                    nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
                }
                // 정류장의 entity list를 하나하나 풀어서 DTO list에 넣어둠
                BoardNodeDomain boardNodeDomain = new BoardNodeDomain();

                boardNodeDomain.setBoardDTO(boardDTO);
                boardNodeDomain.setNodeDTOList(nodeDTOList);
                // 게시글 구성과 정류장 n개를 합쳐서 boardNodeDomain에 저장함

                listAll.add(boardNodeDomain);
                // 게시글 1 완성. 이 과정을 boardEntityList 끝까지 실행함.
            }
            return listAll;
        }
        else {
            return null;
        }
    }
    public BoardDTO findById(Long id){
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            return BoardDTO.toBoardDTO(optionalBoardEntity.get());
            // get 함수를 쓰면 optional로 감싸진 껍데기를 벗길 수 있다.
        }
        else {
            return null;
        }
    }

    public void boardNodesSave(BoardDTO boardDTO, List<NodeDTO> nodeDTOList, String writer) { // 게시글 저장
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO);
        // 게시글 구성을 entity화 시킴
        Optional<MemberEntity> findEmail = memberRepository.findByMemberEmail(writer);
        // 게시글 저장을 누른 사람이 가진 학생수를 가져옴
        boardEntity.setBoardCurrentStudents(findEmail.get().getNumberOfStudents());
        // 이를 entity화시킨 게시글에 학생수를 추가함.
        boardRepository.save(boardEntity); // 저장

        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO); // 하나를 뽑아서 entity화시킴
            nodeEntity.setSameBoardId(boardEntity.getBoardId()); // sameBoardId와 boardId를 연결함
            nodeRepository.save(nodeEntity); // 저장
        }
        // 정류장 하나하나를 Entity화 시키는 과정
    }

    public BoardDTO detailBoard(Long id, String email) { // 게시판 상세보기 (정류장 빼고)
        Optional<BoardEntity> owner = boardRepository.findById(id);
        Optional<MemberEntity> owner2 = memberRepository.findByMemberEmail(email);
        if (owner.isPresent() && owner2.isPresent()) {
            Integer students = owner2.get().getNumberOfStudents();
            return BoardDTO.toBoardDTODetail(owner.get(), students);
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

    @Transactional
    public void updateBoardNode(Long id, BoardDTO boardDTO, List<NodeDTO> nodeDTOList) { // 수정한 게시판 내용 저장하기.
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO);
        boardRepository.updateBoardEntity(id, boardEntity.getBoardTitle(),
                boardEntity.getBoardState(), boardEntity.getBoardCity(), boardEntity.getBoardWhere(),
                boardEntity.getBoardDetail(), boardEntity.getBoardCurrentStudents());

        List<NodeEntity> nodeList = new ArrayList<>();
        nodeRepository.deleteNodeEntities(id);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeEntity.setSameBoardId(id);
            nodeList.add(nodeEntity);
        }
        nodeRepository.saveAll(nodeList);
    }
    // DTO를 Entity 형식으로 수정하고, 각 데이터 하나하나를 직접 지정해서 Quary에 대입함

    @Transactional
    public void deleteBoardNode(Long id) { // 게시물 + 정류장 삭제하기
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

    public void addAllStudents(Long id, String email) {
        Optional<MemberEntity> getStudent = memberRepository.findByMemberEmail(email);
        Optional<BoardEntity> addBoard = boardRepository.findById(id);
        Integer students = getStudent.get().getNumberOfStudents();
        Integer allstudents = addBoard.get().getBoardCurrentStudents() + students;

        boardRepository.addStudents(id, allstudents);
    }

    public List<BoardDTO> myBoardList(String email) {
        List<BoardEntity> myBoardList = boardRepository.findMyBoardList(email);
        List<BoardDTO> boardDTOS = new ArrayList<>();
        if (myBoardList != null) {
            for (BoardEntity myBoard : myBoardList) {
                BoardDTO boardDTO = BoardDTO.toBoardDTO(myBoard);
                boardDTOS.add(boardDTO);
            }
            return boardDTOS;
        }
        else {
            return null;
        }
    }
}
