package com.service;


import com.dto.NodeDTO;
import com.dto.PostDTO;
import com.entity.NodeEntity;
import com.entity.PostEntity;
import com.repository.NodeRepository;
import com.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final NodeRepository nodeRepository;

    public void postSave(PostDTO postDTO, List<NodeDTO> nodeDTOList){ // 게시글 저장
        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postRepository.save(postEntity);
        for(NodeDTO nodeDTO : nodeDTOList){
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }
    public List<PostDTO> postListAll() { // 게시판 리스트 전부 가져오기
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for(PostEntity postEntity : postEntityList){
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    } // 모르겟어 살려줘
    public List<NodeDTO> nodeListAll() { //정류장 리스트 전부 가져오기
        List<NodeEntity> nodeEntityList = nodeRepository.findAll();
        List<NodeDTO> nodeDTOList = new ArrayList<>();
        for(NodeEntity nodeEntity : nodeEntityList){
            if(nodeEntity != null)
                nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
        }
        return nodeDTOList;
    } // Entity의 값이 null이면 빼고 있는 값들만 가져오도록 함.

    public PostDTO detailPost(Long id){ // 게시판 내용(정류장빼고) 상세보기
        Optional<PostEntity> owner = postRepository.findById(id);
        if(owner.isPresent()){
            return PostDTO.toPostDTO(owner.get());
        }
        else
            return null;
    }

    public List<NodeDTO> detailNode(Long id){ // 정류장 상세보기
        List<NodeEntity> nodeEntityList = nodeRepository.findSamePostId(id);
        List<NodeDTO> nodeDTOList = new ArrayList<>();
        if(!nodeEntityList.isEmpty()){
            for(NodeEntity nodeEntity : nodeEntityList){
                nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
            }
            return nodeDTOList;
        }
        else
            return null;
    }

    public void updatePostNode(PostDTO postDTO, List<NodeDTO> nodeDTOList){ // 수정한 게시판 내용 저장하기
        postRepository.save(PostEntity.toUpdatePostEntity(postDTO));
        for(NodeDTO nodeDTO : nodeDTOList){
            NodeEntity nodeEntity = NodeEntity.toUpdateNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }

    public void deletePostNode(Long id) {
        postRepository.deleteById(id);
        nodeRepository.deleteNodeEntities(id);
    }

}
