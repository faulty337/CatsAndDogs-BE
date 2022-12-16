package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
