package com.spring.butch.controller;

import com.spring.butch.dto.NodeDTO;
import com.spring.butch.dto.PostDTO;
import com.spring.butch.domain.PostNodeDomain;
import com.spring.butch.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/post/list") // 게시글 데이터 전부 가져오기
    public ResponseEntity<List<Object>> postNodeList(){
        List<PostDTO> postDTOList = postService.postListAll();
        List<NodeDTO> nodeDTOList = postService.nodeListAll();

        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(postDTOList);
        combinedList.addAll(nodeDTOList);

        return ResponseEntity.ok(combinedList);
    }

    @PostMapping("/post/write") //게시글 저장하기
    public ResponseEntity<String> postSave(@RequestBody PostNodeDomain postNode){
        PostDTO postDTO = postNode.getPostDTO();
        List<NodeDTO> nodeDTOList = postNode.getNodeDTOList();

        System.out.println("Postlist save");
        System.out.println("게시물 구성 : " + postDTO);
        System.out.println("정류장 종류 : " + nodeDTOList);
        postService.postSave(postDTO, nodeDTOList);

        return ResponseEntity.ok("Post Save");
    }

    @GetMapping("/post/detail/{id}")// 게시글 상세보기
    public ResponseEntity<PostNodeDomain> detailPost(@PathVariable Long id) {
        PostDTO detailPost = postService.detailPost(id);
        List<NodeDTO> detailNode = postService.detailNode(id);

        PostNodeDomain postDetailResponse = new PostNodeDomain(detailPost, detailNode);
        return ResponseEntity.ok(postDetailResponse);
    }

    @PostMapping("/post/update/{id}")
    public ResponseEntity<String> postUpdate(@RequestBody PostNodeDomain postNode) {
        PostDTO postDTO = postNode.getPostDTO();
        List<NodeDTO> nodeDTOList = postNode.getNodeDTOList();

        System.out.println("Postlist update");
        System.out.println("게시물 수정 : " + postDTO);
        System.out.println("정류장 수정 : " + nodeDTOList);
        postService.updatePostNode(postDTO, nodeDTOList);

        return ResponseEntity.ok("Post Update");
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePostNode(id);
        return ResponseEntity.ok("Post Delete");
    }

}



