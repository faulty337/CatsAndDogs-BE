package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.LikeCommentResponseDto;
import com.hanghae99.catsanddogs.dto.LikePostResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.entity.*;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.CommentRepository;
import com.hanghae99.catsanddogs.repository.LikeCommentRepository;
import com.hanghae99.catsanddogs.repository.LikePostRepository;
import com.hanghae99.catsanddogs.repository.PostRepository;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Service
public class LikeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public LikePostResponseDto likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        Long userId = user.getId();

        boolean likeCheck;

        Optional <LikePost> likePost = likePostRepository.findByPostIdAndUserId(postId, userId);

        if (likePost.isPresent()) {
            LikePost like = likePost.get();
            likePostRepository.delete(like);
            post.setLikeCount(post.getLikeCount()-1);

            likeCheck = false;

        } else{
            LikePost like = new LikePost(postId, userId);
            likePostRepository.save(like);
            post.setLikeCount(post.getLikeCount()+1);
            likeCheck = true;
        }


        return new LikePostResponseDto(likeCheck, post.getLikeCount());
    }

    public LikeCommentResponseDto likeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        Long userId = user.getId();

        boolean likeCheck;

        Optional<LikeComment> likeComment = likeCommentRepository.findByCommentIdAndUserId(commentId, userId);

        if(likeComment.isPresent()){
            LikeComment like = likeComment.get();
            likeCommentRepository.delete(like);
            comment.setLikeCount(comment.getLikeCount()-1);
            likeCheck = false;

        } else{
            LikeComment like = new LikeComment(commentId, userId);
            comment.setLikeCount(comment.getLikeCount()+1);
            likeCommentRepository.save(like);
            likeCheck = true;
        }

        return new LikeCommentResponseDto(likeCheck, comment.getLikeCount());
    }
}
