package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.LikeCommentResponseDto;
import com.hanghae99.catsanddogs.dto.LikePostResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RequestMapping("/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<ResponseMessage> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        boolean postLiked = likeService.likePost(postId, userDetails.getUser());
        LikePostResponseDto likePostResponseDto = new LikePostResponseDto(postLiked);

        ResponseMessage<LikePostResponseDto> responseMessage = new ResponseMessage<>("게시글 좋아요 성공", 200, likePostResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ResponseMessage> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        boolean commentLiked = likeService.likeComment(commentId, userDetails.getUser());
        LikeCommentResponseDto likeCommentResponseDto = new LikeCommentResponseDto(commentLiked);

        ResponseMessage<LikeCommentResponseDto> responseMessage = new ResponseMessage<>("댓글 좋아요 성공", 200, likeCommentResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}
