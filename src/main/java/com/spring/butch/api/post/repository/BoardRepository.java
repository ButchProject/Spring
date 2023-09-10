package com.spring.butch.api.post.repository;

import com.spring.butch.api.post.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
//    @Query ("SELECT P FROM PostEntity p WHERE p.postWhere = :postWhere")
//    List<PostEntity> findSameWhere (@Param("postWhere") String postWhere);

    @Modifying
    @Transactional
    @Query("UPDATE BoardEntity b SET " +
            "b.boardTitle =  :boardTitle," +
            "b.boardState = :boardState," +
            "b.boardCity = :boardCity," +
            "b.boardWhere = :boardWhere," +
            "b.boardDetail = :boardDetail," +
            "b.boardCurrentMoney = :boardCurrentMoney," +
            "b.boardSaleMoney = :boardSaleMoney " +
            "WHERE b.boardId = :boardId")
    void updateBoardEntity (@Param("boardId") Long boardId,
                           @Param("boardTitle") String boardTitle,
                           @Param("boardState") String boardState,
                           @Param("boardCity") String boardCity,
                            @Param("boardWhere") String boardWhere,
                            @Param("boardDetail") String boardDetail,
                           @Param("boardCurrentMoney") Integer boardCurrentMoney,
                            @Param("boardSaleMoney") Integer boardSaleMoney
                           );


    @Query ("SELECT b FROM BoardEntity b ORDER BY b.boardId DESC")
    List<BoardEntity> sortBoardListByDesc();
}
