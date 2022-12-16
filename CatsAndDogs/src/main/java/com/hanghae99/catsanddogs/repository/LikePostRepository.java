package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
}
