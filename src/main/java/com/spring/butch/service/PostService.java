package com.spring.butch.service;


import com.spring.butch.dto.NodeDTO;
import com.spring.butch.dto.PostDTO;
import com.spring.butch.entity.NodeEntity;
import com.spring.butch.entity.PostEntity;
import com.spring.butch.repository.NodeRepository;
import com.spring.butch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final NodeRepository nodeRepository;

    public void postSave(PostDTO postDTO, List<NodeDTO> nodeDTOList) { // 게시글 저장
        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postRepository.save(postEntity);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }

    public List<PostDTO> postListAll() { // 게시판 리스트 전부 가져오기
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    } // 모르겟어 살려줘

    public List<NodeDTO> nodeListAll() { //정류장 리스트 전부 가져오기
        List<NodeEntity> nodeEntityList = nodeRepository.findAll();
        List<NodeDTO> nodeDTOList = new ArrayList<>();
        for (NodeEntity nodeEntity : nodeEntityList) {
            if (nodeEntity != null)
                nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
        }
        return nodeDTOList;
    } // Entity의 값이 null이면 빼고 있는 값들만 가져오도록 함.

    public PostDTO detailPost(Long id) { // 게시판 내용(정류장빼고) 상세보기
        Optional<PostEntity> owner = postRepository.findById(id);
        if (owner.isPresent()) {
            return PostDTO.toPostDTO(owner.get());
        } else
            return null;
    }

    public List<NodeDTO> detailNode(Long id) { // 정류장 상세보기
        List<NodeEntity> nodeEntityList = nodeRepository.findSamePostId(id);
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
    public void updatePostNode(Long id, PostDTO postDTO, List<NodeDTO> nodeDTOList) {
        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postRepository.updatePostEntitiy(id, postEntity.getPostTitle(),
                postEntity.getPostWhere(), postEntity.getPostDetail(), postEntity.getPostMoney(),
                postEntity.getPostSaleMoney());
        nodeRepository.deleteNodeEntities(id);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }// 수정한 게시판 내용 저장하기.
    // DTO를 Entity 형식으로 수정하고, 각 데이터 하나하나를 직접 지정해서 Quary에 대입함

    @Transactional
    public void deletePostNode(Long id) {
        postRepository.deleteById(id);
        nodeRepository.deleteNodeEntities(id);
    } // 게시물 삭제하기
    // 게시물에 대한 아이디 정보만 받음. 아이디에 일치되는 게시물 데이터 전부 삭제

}
