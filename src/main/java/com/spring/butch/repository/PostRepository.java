package com.spring.butch.repository;

import com.spring.butch.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
//    @Query ("SELECT P FROM PostEntity p WHERE p.postWhere = :postWhere")
//    List<PostEntity> findSameWhere (@Param("postWhere") String postWhere);

    @Modifying
    @Transactional
    @Query("UPDATE PostEntity p SET " +
            "p.postTitle =  :postTitle," +
            "p.postWhere = :postWhere," +
            "p.postDetail = :postDetail," +
            "p.postCapacityStudent = :postCapacityStudent," +
            "p.postCurrentStudent = :postCurrentStudent," +
            "p.postMoney = :postMoney," +
            "p.postSaleMoney = :postSaleMoney," +
            "p.postDay = :postDay " +
            "WHERE p.postId = :postId")
    void updatePostEntitiy (@Param("postId") Long postId,
                           @Param("postTitle") String postTitle,
                           @Param("postWhere") String postWhere,
                           @Param("postDetail") String postDetail,
                           @Param("postCapacityStudent") Long postCapacityStudent,
                           @Param("postCurrentStudent") Long postCurrentStudent,
                           @Param("postMoney") Long postMoney,
                           @Param("postSaleMoney") Long postSaleMoney,
                           @Param("postDay") String postDay);


    @Query ("SELECT p.postTitle, p.postWhere, p.postCapacityStudent, p.postCurrentStudent " +
            "FROM PostEntity p " +
            "ORDER BY p.postId DESC")
    List<PostEntity> sortPostListByDesc();
}
