package com.spring.butch.api.board.entity;

import com.spring.butch.api.board.dto.NodeDTO;
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
    @Column (name = "same_board_id")
    private Long sameBoardId; // 게시물 아이디
    @Column (name = "node_num")
    private Long nodeNum; // 정류장 번호
    @Column (name = "latitude")
    private Double latitude; // 위도
    @Column (name = "longitude")
    private Double longitude; // 경도
    @Column (name = "node_name")
    private String nodeName; // 정류장 이름
    @Column (name = "node_detail")
    private String nodeDetail; // 정류장 세부지역
    @Column (name = "node_hour")
    private Integer nodeHour; // 정류장 시간
    @Column (name = "node_minute")
    private Integer nodeMinute; // 정류장 분

    public static NodeEntity toNodeEntity(NodeDTO nodeDTO){
        NodeEntity nodeEntity = new NodeEntity();

        nodeEntity.setSameBoardId(nodeDTO.getSameBoardId());
        nodeEntity.setNodeNum(nodeDTO.getNodeNum());
        nodeEntity.setLatitude(nodeDTO.getLatitude());
        nodeEntity.setLongitude(nodeDTO.getLongitude());
        nodeEntity.setNodeName(nodeDTO.getNodeName());
        nodeEntity.setNodeDetail(nodeDTO.getNodeDetail());
        nodeEntity.setNodeHour(nodeDTO.getNodeHour());
        nodeEntity.setNodeMinute(nodeDTO.getNodeMinute());

        return nodeEntity;
    }
}
