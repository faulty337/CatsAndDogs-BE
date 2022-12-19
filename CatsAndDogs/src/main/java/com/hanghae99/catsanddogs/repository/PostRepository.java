package com.hanghae99.catsanddogs.repository;

import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop2ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime localDateTime);
    Optional<Post> findByIdAndNickname(Long postId, String nickname);
}
