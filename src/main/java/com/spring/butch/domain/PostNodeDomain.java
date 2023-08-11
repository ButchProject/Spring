package com.spring.butch.domain;

import com.spring.butch.dto.NodeDTO;
import com.spring.butch.dto.PostDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class PostNodeDomain {
        private PostDTO postDTO;
        private List<NodeDTO> nodeDTOList;

    public PostNodeDomain(PostDTO postDTO, List<NodeDTO> nodeDTOList) {
        this.postDTO = postDTO;
        this.nodeDTOList = nodeDTOList;
    }
}
