package com.spring.butch.dto;

import com.spring.butch.entity.NodeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NodeDTO {
    // 위도와 경도를 따로 저장해둔다.
    private Long nodeId;
    private Long postId; // 게시물 아이디
    private Long nodeNum; // 정류장 번호
    private Double latitude; // 위도
    private Double longtitude; // 경도
    private String nodeName; // 정류장 이름
    private String nodeDetail; // 정류장 세부지역
    private String nodeTime; // 정류장 도착시간

    public static NodeDTO toNodeDTO(NodeEntity nodeEntity){
        NodeDTO nodeDTO = new NodeDTO();
        nodeDTO.setNodeId(nodeEntity.getNodeId());
        nodeDTO.setPostId(nodeEntity.getPostId());
        nodeDTO.setNodeNum(nodeEntity.getNodeNum());
        nodeDTO.setLatitude(nodeEntity.getLatitude());
        nodeDTO.setLongtitude(nodeEntity.getLongtitude());
        nodeDTO.setNodeName(nodeEntity.getNodeName());
        nodeDTO.setNodeDetail(nodeEntity.getNodeDetail());
        nodeDTO.setNodeTime(nodeEntity.getNodeTime());

        return nodeDTO;
    }
}
