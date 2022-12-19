package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop2ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime localDateTime);
}
