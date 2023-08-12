package com.spring.butch.repository;

import com.spring.butch.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE PostEntity p SET " +
            "p.postTitle =  :postTitle," +
            "p.postWhere = :postWhere," +
            "p.postDetail = :postDetail," +
            "p.postMoney = :postMoney," +
            "p.postSaleMoney = :postSaleMoney " +
            "WHERE p.postId = :postId")
    void updatePostEntitiy(@Param("postId") Long postId,
                            @Param("postTitle") String postTitle,
                            @Param("postWhere") String postWhere,
                            @Param("postDetail") String postDetail,
                            @Param("postMoney") Long postMoney,
                            @Param("postSaleMoney") Long postSaleMoney);
}
