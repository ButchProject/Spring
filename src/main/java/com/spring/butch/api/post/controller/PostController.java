package com.spring.butch.api.post.controller;

import com.spring.butch.api.member.service.SecurityService;
import com.spring.butch.api.post.dto.NodeDTO;
import com.spring.butch.api.post.dto.PostDTO;
import com.spring.butch.api.post.service.PostService;
import com.spring.butch.api.post.domain.PostNodeDomain;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final SecurityService securityService;

    @GetMapping("/Board")
    public ResponseEntity<List<PostNodeDomain>> postNodeList(HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);

        List<PostNodeDomain> postNodeListAll = postService.postNodeListAll();

        return ResponseEntity.ok(postNodeListAll);
    }


    @PostMapping("/writingBoard") //게시글 저장하기
    public ResponseEntity<String> postSave(@RequestBody PostNodeDomain postNode, HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);

        PostDTO postDTO = postNode.getPostDTO();
        List<NodeDTO> nodeDTOList = postNode.getNodeDTOList();

        System.out.println("Postlist save");
        System.out.println("게시물 구성 : " + postDTO);
        System.out.println("정류장 종류 : " + nodeDTOList);
        postService.postSave(postDTO, nodeDTOList);

        return ResponseEntity.ok("Post Save");
    }

    @GetMapping("/detailBoard/{id}")// 게시글 상세보기
    public ResponseEntity<PostNodeDomain> detailPost(@PathVariable Long id, HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);
        String email = claims.getSubject();

        PostDTO detailPost = postService.detailPost(id, email);
        List<NodeDTO> detailNode = postService.detailNode(id);

        PostNodeDomain postDetailResponse = new PostNodeDomain(detailPost, detailNode);
        return ResponseEntity.ok(postDetailResponse);
    }

    @PostMapping("/updateBoard/{id}") // 게시글 수정하기
    public ResponseEntity<String> postUpdate(@PathVariable Long id, @RequestBody PostNodeDomain postNode, HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);

        PostDTO postDTO = postNode.getPostDTO();
        List<NodeDTO> nodeDTOList = postNode.getNodeDTOList();

        System.out.println("Postlist update");
        System.out.println("게시물 수정 : " + postDTO);
        System.out.println("정류장 수정 : " + nodeDTOList);
        postService.updatePostNode(id, postDTO, nodeDTOList);

        return ResponseEntity.ok("Post Update");
    }

    @DeleteMapping("/deleteBoard/{id}") // 게시글 삭제하기
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);

        postService.deletePostNode(id);
        return ResponseEntity.ok("Post Delete");
    }

//    @GetMapping("/post/list/sortByDesc") // 게시글 최신순으로 정렬하기
//    public ResponseEntity<List<PostDTO>> sortByDesc() {
//        List<PostDTO> postDTOList = postService.sortPostListByDecs();
//        return ResponseEntity.ok(postDTOList);
//    }
    // 가져오는 데이터는 게시글 목록에 필요한 내용만 가져옴
    // 제목, 상세지역, 전체 학생수, 현재 학생수
    // 정렬해서 가져오기

}



