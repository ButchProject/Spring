package com.controller;

import com.dto.NodeDTO;
import com.dto.PostDTO;
import com.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
class PostNode {
    private PostDTO postDTO;
    private List<NodeDTO> nodeDTOList;

    public PostNode(PostDTO postDTO, List<NodeDTO> nodeDTOList) {
        this.postDTO = postDTO;
        this.nodeDTOList = nodeDTOList;
    }
}


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
    public ResponseEntity<String> postSave(@RequestBody PostNode postNode){
        PostDTO postDTO = postNode.getPostDTO();
        List<NodeDTO> nodeDTOList = postNode.getNodeDTOList();

        System.out.println("Postlist save");
        System.out.println("게시물 구성 : " + postDTO);
        System.out.println("정류장 종류 : " + nodeDTOList);
        postService.postSave(postDTO, nodeDTOList);

        return ResponseEntity.ok("Post Save");
    }

    @GetMapping("/post/detail/{id}")// 게시글 상세보기
    public ResponseEntity<PostNode> detailPost(@PathVariable Long id) {
        PostDTO detailPost = postService.detailPost(id);
        List<NodeDTO> detailNode = postService.detailNode(id);

        PostNode postDetailResponse = new PostNode(detailPost, detailNode);
        return ResponseEntity.ok(postDetailResponse);
    }

    @PostMapping("/post/update/{id}")
    public ResponseEntity<String> postUpdate(@RequestBody PostNode postNode) {
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



