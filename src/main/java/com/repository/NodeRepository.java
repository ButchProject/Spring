package com.repository;

import com.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    @Query("SELECT n FROM NodeEntity n WHERE n.postId = :id")
    List<NodeEntity> findSamePostId(@Param("id") Long id);

    @Query("DELETE FROM NodeEntity n WHERE n.postId = :id")
    List<NodeEntity> deleteNodeEntities(@Param("id") Long id);
}
