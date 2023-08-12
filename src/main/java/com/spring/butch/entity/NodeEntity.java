package com.spring.butch.entity;

import com.spring.butch.dto.NodeDTO;
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
    private Long postId; // 게시물 아이디
    @Column
    private Long nodeNum; // 정류장 번호
    @Column
    private Double latitude; // 위도
    @Column
    private Double longtitude; // 경도
    @Column(nullable = false)
    private String nodeName; // 정류장 이름
    @Column(nullable = false)
    private String nodeDetail; // 정류장 세부지역
    @Column(nullable = false)
    private String nodeTime;

    public static NodeEntity toNodeEntity(NodeDTO nodeDTO){
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setPostId(nodeDTO.getPostId());
        nodeEntity.setNodeNum(nodeDTO.getNodeNum());
        nodeEntity.setLatitude(nodeDTO.getLatitude());
        nodeEntity.setLongtitude(nodeDTO.getLongtitude());
        nodeEntity.setNodeName(nodeDTO.getNodeName());
        nodeEntity.setNodeDetail(nodeDTO.getNodeDetail());
        nodeEntity.setNodeTime(nodeDTO.getNodeTime());

        return nodeEntity;
    }
}
