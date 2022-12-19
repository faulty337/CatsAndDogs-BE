package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByCommentIdAndUserId(Long commentId, Long userId);
    void deleteAllByCommentId(Long commentId);
}
