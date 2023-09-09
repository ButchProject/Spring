package com.spring.butch.api.post.service;


import com.spring.butch.api.member.entity.MemberEntity;
import com.spring.butch.api.member.repository.MemberRepository;
import com.spring.butch.api.post.domain.PostNodeDomain;
import com.spring.butch.api.post.dto.NodeDTO;
import com.spring.butch.api.post.dto.PostDTO;
import com.spring.butch.api.post.entity.NodeEntity;
import com.spring.butch.api.post.entity.PostEntity;
import com.spring.butch.api.post.repository.NodeRepository;
import com.spring.butch.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final NodeRepository nodeRepository;
    private final MemberRepository memberRepository;

    public void postSave(PostDTO postDTO, List<NodeDTO> nodeDTOList) { // 게시글 저장
        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postRepository.save(postEntity);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeEntity.setSamePostId(postEntity.getPostId()); // samePostId와 postId를 연결함
            nodeRepository.save(nodeEntity);
        }
    }

    public List<PostNodeDomain> postNodeListAll() { // 게시글 리스트 전부 가져오기(최신순으로)
        List<PostEntity> postEntityList = postRepository.sortPostListByDesc(); // 최신순으로 게시글 db가져오기
        List<PostNodeDomain> listAll = new ArrayList<>();

        if(postEntityList != null) {
            for(PostEntity postEntity : postEntityList) {
                PostDTO postDTO = PostDTO.toPostDTO(postEntity);
                List<NodeDTO> nodeDTOList = new ArrayList<>();
                List<NodeEntity> nodeEntities = nodeRepository.findSamePostIdNode(postEntity.getPostId());
                // 게시글에 대한 정류장 뽑아서 가져와주기

                for(NodeEntity nodeEntity : nodeEntities) {
                    nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
                }
                PostNodeDomain postNodeDomain = new PostNodeDomain();

                postNodeDomain.setPostDTO(postDTO);
                postNodeDomain.setNodeDTOList(nodeDTOList);

                listAll.add(postNodeDomain);
            }
            return listAll;
        }

        else {
            return null;
        }

    }

//    public List<PostDTO> postListAll() { // 게시판 리스트 전부 가져오기
//        List<PostEntity> postEntityList = postRepository.findAll();
//        List<PostDTO> postDTOList = new ArrayList<>();
//        for (PostEntity postEntity : postEntityList) {
//            postDTOList.add(PostDTO.toPostDTO(postEntity));
//        }
//        return postDTOList;
//    }
//
//    public List<NodeDTO> nodeListAll() { // 정류장 리스트 전부 가져오기
//        List<NodeEntity> nodeEntityList = nodeRepository.findAll();
//        List<NodeDTO> nodeDTOList = new ArrayList<>();
//        for (NodeEntity nodeEntity : nodeEntityList) {
//            if (nodeEntity != null)
//                nodeDTOList.add(NodeDTO.toNodeDTO(nodeEntity));
//            else
//                return null;
//        }
//        return nodeDTOList;
//    } // Entity의 값이 null이면 빼고 있는 값들만 가져오도록 함.

    public PostDTO detailPost(Long id, String email) { // 게시판 내용(정류장빼고) 상세보기
        Optional<PostEntity> owner = postRepository.findById(id);
        Optional<MemberEntity> memberinformaiton = memberRepository.findByMemberEmail(email);
        Integer students = memberinformaiton.get().getNumberOfStudents();

        if (owner.isPresent()) {
            PostDTO postDTO = PostDTO.toPostDTO(owner.get());
            postDTO.setPostBusSaleMoney(students);
            return postDTO;
        } else
            return null;
    }

    public List<NodeDTO> detailNode(Long id) { // 정류장 상세보기
        List<NodeEntity> nodeEntityList = nodeRepository.findSamePostIdNode(id);
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
    public void updatePostNode(Long id, PostDTO postDTO, List<NodeDTO> nodeDTOList) { // 수정한 게시판 내용 저장하기.
        PostEntity postEntity = PostEntity.toPostEntity(postDTO);
        postRepository.updatePostEntitiy(id, postEntity.getPostTitle(),
                postEntity.getPostWhere(), postEntity.getPostDetail(), postEntity.getPostBus45(),
                postEntity.getPostBus25(), postEntity.getPostBus12(), postEntity.getPostCurrentStudent());
        nodeRepository.deleteNodeEntities(id);
        for (NodeDTO nodeDTO : nodeDTOList) {
            NodeEntity nodeEntity = NodeEntity.toNodeEntity(nodeDTO);
            nodeRepository.save(nodeEntity);
        }
    }
    // DTO를 Entity 형식으로 수정하고, 각 데이터 하나하나를 직접 지정해서 Quary에 대입함

    @Transactional
    public void deletePostNode(Long id) { // 게시물 삭제하기
        postRepository.deleteById(id);
        nodeRepository.deleteNodeEntities(id);
    }
    // 게시물에 대한 아이디 정보만 받음. 아이디에 일치되는 게시물 데이터 전부 삭제
    // 즉, postEntity에서는 데이터 행 하나만 삭제
    // nodeEntity에서는 여러 개의 행 삭제



}
