package com.spring.butch.api.post.repository;

import com.spring.butch.api.post.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
    @Query("SELECT n FROM NodeEntity n WHERE n.postId = :id")
    List<NodeEntity> findSamePostIdNode(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM NodeEntity n WHERE n.postId = :id")
    void deleteNodeEntities(@Param("id") Long id);
}
