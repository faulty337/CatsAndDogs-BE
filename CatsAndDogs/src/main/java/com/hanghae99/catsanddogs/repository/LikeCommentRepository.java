package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByCommentIdAndUserId(Long commentId, Long userId);


    @Modifying
    @Query("DELETE from LikeComment c where c.commentId = :id")
    void deleteAllByCommentId(@Param("id") Long postId);


    @Modifying
    @Query("DELETE from LikeComment c where c.commentId in :ids")
    void deleteAllByCommentIdIn(@Param("ids") List<Long> ids);
}
