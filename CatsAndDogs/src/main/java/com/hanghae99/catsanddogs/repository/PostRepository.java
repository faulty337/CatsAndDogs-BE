package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndNickname(Long postId, String nickname);
}
