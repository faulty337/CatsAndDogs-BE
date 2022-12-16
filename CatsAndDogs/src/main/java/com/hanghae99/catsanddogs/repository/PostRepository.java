package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
