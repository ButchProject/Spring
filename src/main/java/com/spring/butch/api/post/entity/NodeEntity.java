package com.spring.butch.api.post.entity;

import com.spring.butch.api.post.dto.NodeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "node_table")
public class NodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long nodeId;
    @Column
    private Long samePostId; // 게시물 아이디
    @Column
    private Long nodeNum; // 정류장 번호
    @Column
    private Double latitude; // 위도
    @Column
    private Double longtitude; // 경도
    @Column
    private String nodeName; // 정류장 이름
    @Column
    private String nodeDetail; // 정류장 세부지역
    @Column
    private Integer nodeHour; // 정류장 시간
    @Column
    private Integer nodeMinute; // 정류장 분

    public static NodeEntity toNodeEntity(NodeDTO nodeDTO){
        NodeEntity nodeEntity = new NodeEntity();

        nodeEntity.setSamePostId(nodeDTO.getSamePostId());
        nodeEntity.setNodeNum(nodeDTO.getNodeNum());
        nodeEntity.setLatitude(nodeDTO.getLatitude());
        nodeEntity.setLongtitude(nodeDTO.getLongtitude());
        nodeEntity.setNodeName(nodeDTO.getNodeName());
        nodeEntity.setNodeDetail(nodeDTO.getNodeDetail());
        nodeEntity.setNodeHour(nodeDTO.getNodeHour());
        nodeEntity.setNodeMinute(nodeDTO.getNodeMinute());

        return nodeEntity;
    }
}
