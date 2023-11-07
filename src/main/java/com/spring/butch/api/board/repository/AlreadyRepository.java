package com.spring.butch.api.board.repository;

import com.spring.butch.api.board.entity.AlreadyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AlreadyRepository extends JpaRepository<AlreadyEntity, Long> {
    @Query("SELECT a FROM AlreadyEntity a WHERE a.email = :email")
    Optional<AlreadyEntity> findByEmail(@Param("email") String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM AlreadyEntity a WHERE a.postId = :id")
    void deleteByPostId(@Param("id") Long id);
}
