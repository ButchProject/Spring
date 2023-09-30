package com.spring.butch.api.board.controller;

import com.spring.butch.api.member.service.SecurityService;
import com.spring.butch.api.board.dto.NodeDTO;
import com.spring.butch.api.board.dto.BoardDTO;
import com.spring.butch.api.board.service.BoardService;
import com.spring.butch.api.board.domain.BoardNodeDomain;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final SecurityService securityService;

    @GetMapping("/Board") // 게시글 목록
    public ResponseEntity<List<BoardNodeDomain>> postNodeList(HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사

        List<BoardNodeDomain> boardNodeListAll = boardService.boardNodeListAll(); // 리스트 전부 보여주기

        return ResponseEntity.ok(boardNodeListAll);
    }


    @PostMapping("/writingBoard") // 게시글 저장하기
    public ResponseEntity<String> postSave(@RequestBody BoardNodeDomain boardNode, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사
        String writer = claims.getSubject();

        BoardDTO boardDTO = boardNode.getBoardDTO();
        List<NodeDTO> nodeDTOList = boardNode.getNodeDTOList(); // 게시글, 정류장 나눠담기
        boardDTO.setBoardWriter(writer); // 토큰에 있는 email 가져와서 DTO에 세팅하기

        System.out.println("Boardlist save");
        System.out.println("게시물 구성 : " + boardDTO);//
        System.out.println("정류장 종류 : " + nodeDTOList); // 저장된 원소 보여주기
        boardService.boardNodesSave(boardDTO, nodeDTOList, writer); // 저장

        return ResponseEntity.ok("Board Save");
    }

    @GetMapping("/detailBoard/{id}")// 게시글 상세보기
    public ResponseEntity<BoardNodeDomain> detailPost(@PathVariable Long id, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사
        String email = claims.getSubject(); // 토큰 이메일 따오기

        BoardDTO detailBoard = boardService.detailBoard(id, email);
        List<NodeDTO> detailNode = boardService.detailNode(id);

        BoardNodeDomain boardNodeDomain = new BoardNodeDomain(detailBoard, detailNode);
        return ResponseEntity.ok(boardNodeDomain);
    }

    @GetMapping("updateBoard/{id}") // 게시글 수정 ( 기존데이터 가져오기 )
    public ResponseEntity<BoardNodeDomain> myBoard(@PathVariable Long id, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사
        String email = claims.getSubject();

        BoardDTO detailBoard = boardService.detailBoard(id, email);
        List<NodeDTO> detailNode = boardService.detailNode(id);

        BoardNodeDomain boardNodeDomain = new BoardNodeDomain(detailBoard, detailNode);
        return ResponseEntity.ok(boardNodeDomain);
    }
    @PostMapping("/updateBoard/{id}") // 게시글 수정하기 ( 수정한 게시글 저장하기 )
    public ResponseEntity<String> postUpdate(@PathVariable Long id, @RequestBody BoardNodeDomain boardNode, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사

        BoardDTO boardDTO = boardNode.getBoardDTO();
        List<NodeDTO> nodeDTOList = boardNode.getNodeDTOList(); // 게시글, 정류장 나누기

        System.out.println("Boardlist update");
        System.out.println("게시물 수정 : " + boardDTO); // 들어온 데이터 터미널로 확인하기
        System.out.println("정류장 수정 : " + nodeDTOList); // 들어온 데이터 터미널로 확인하기
        boardService.updateBoardNode(id, boardDTO, nodeDTOList); // 업데이트 로직
        // 게시글이 업데이트 되면, 해당 게시글 업로드 날짜 변경됨.

        return ResponseEntity.ok("Board Update");
    }

    @PostMapping("detailBoard/{id}/add") // 학생 수 추가하기
    public ResponseEntity<String> addStudentBoard(@PathVariable Long id, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사
        String email = claims.getSubject(); // 토큰 이메일 따오기

        boardService.addAllStudents(id, email); // 학생 더하기 로직

        return ResponseEntity.ok("add Student");
    }

    @DeleteMapping("/deleteBoard/{id}") // 게시글 삭제하기
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사

        boardService.deleteBoardNode(id);// 삭제 로직
        return ResponseEntity.ok("Board Delete");
    }

    @GetMapping("/searchBoard/{city}") // 원하는 지역 검색해서 보기
    public ResponseEntity<List<BoardDTO>> searchBoard(@PathVariable String city, HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사

        List<BoardDTO> boardDTOList= boardService.searchWantCity(city);

        return ResponseEntity.ok(boardDTOList);
    }

}



