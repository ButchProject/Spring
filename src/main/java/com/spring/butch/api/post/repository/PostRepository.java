package com.spring.butch.api.post.repository;

import com.spring.butch.api.post.entity.PostEntity;
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
            "p.postBus45 = :postBus45," +
            "p.postBus25 = :postBus25," +
            "p.postBus12 = :postBus12," +
            "p.postCurrentStudent = :postCurrentStudent " +
            "WHERE p.postId = :postId")
    void updatePostEntitiy (@Param("postId") Long postId,
                           @Param("postTitle") String postTitle,
                           @Param("postWhere") String postWhere,
                           @Param("postDetail") String postDetail,
                           @Param("postBus45") Integer postBus45,
                            @Param("postBus25") Integer postBus25,
                            @Param("postBus12") Integer postBus12,
                            @Param("postCurrentStudent") Integer postCurrentStudent
                           );


    @Query ("SELECT p FROM PostEntity p ORDER BY p.postId DESC")
    List<PostEntity> sortPostListByDesc();
}
