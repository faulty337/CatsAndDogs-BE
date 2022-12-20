package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByPostIdAndUserId(Long postId, Long userId);

    Long countByPostId(Long postId);
}
