package com.spring.butch.api.post.dto;

import com.spring.butch.api.post.entity.NodeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NodeDTO {
    private Long nodeId;
    private Long samePostId; // 게시물 아이디
    private Long nodeNum; // 정류장 번호
    private Double latitude; // 위도
    private Double longtitude; // 경도
    private String nodeName; // 정류장 이름
    private String nodeDetail; // 정류장 세부지역
    private Integer nodeHour; // 정류장 시간
    private Integer nodeMinute; // 정류장 분

    public static NodeDTO toNodeDTO(NodeEntity nodeEntity){
        NodeDTO nodeDTO = new NodeDTO();

        nodeDTO.setNodeId(nodeEntity.getNodeId());
        nodeDTO.setSamePostId(nodeEntity.getSamePostId());
        nodeDTO.setNodeNum(nodeEntity.getNodeNum());
        nodeDTO.setLatitude(nodeEntity.getLatitude());
        nodeDTO.setLongtitude(nodeEntity.getLongtitude());
        nodeDTO.setNodeName(nodeEntity.getNodeName());
        nodeDTO.setNodeDetail(nodeEntity.getNodeDetail());
        nodeDTO.setNodeHour(nodeEntity.getNodeHour());
        nodeDTO.setNodeMinute(nodeEntity.getNodeMinute());

        return nodeDTO;
    }
}
