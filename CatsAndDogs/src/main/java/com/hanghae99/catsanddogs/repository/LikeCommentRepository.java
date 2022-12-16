package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
