package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByPostIdAndUserId(Long postId, Long userId);

    Long countByPostId(Long postId);

    @Modifying
    @Query("DELETE from LikePost c where c.postId = :id")
    void deleteAllByPostId(@Param("id") Long postId);
}
