package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.LikeCommentResponseDto;
import com.hanghae99.catsanddogs.dto.LikePostResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.repository.CommentRepository;
import com.hanghae99.catsanddogs.repository.LikeCommentRepository;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/post/{postId}")
    public ResponseEntity<ResponseMessage> likePost(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){

        LikePostResponseDto likePostResponseDto = likeService.likePost(postId, userDetails.getUser());

        ResponseMessage<LikePostResponseDto> responseMessage = new ResponseMessage<>("게시글 좋아요 성공", 200, likePostResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @ApiOperation(value = "댓글 좋아요")
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ResponseMessage> likeComment(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){


        LikeCommentResponseDto likeCommentResponseDto = likeService.likeComment(commentId, userDetails.getUser());

        ResponseMessage<LikeCommentResponseDto> responseMessage = new ResponseMessage<>("댓글 좋아요 성공", 200, likeCommentResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }


}
