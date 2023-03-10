package com.hanghae99.catsanddogs.service;


import com.hanghae99.catsanddogs.dto.comment.CommentRequestDto;
import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.entity.Comment;
import com.hanghae99.catsanddogs.entity.Post;
import com.hanghae99.catsanddogs.entity.User;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.CommentRepository;
import com.hanghae99.catsanddogs.repository.LikeCommentRepository;
import com.hanghae99.catsanddogs.repository.PostRepository;
import com.hanghae99.catsanddogs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final LikeCommentRepository likeCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentResponseDto createComment(Long postId, Long userId, String content) {
        User user =userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));
        Post post =postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        Comment comment = commentRepository.save(new Comment(content, user));
        post.addComment(comment);
        return new CommentResponseDto(comment, userId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if(!comment.getUser().getId().equals(userId)){
            throw  new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }
        if(!post.getCommentList().contains(comment)){
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        likeCommentRepository.deleteAllByCommentId(commentId);
        commentRepository.delete(comment);
    }


}
