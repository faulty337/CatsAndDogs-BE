package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
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
        likeService.likePost(postId);

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ResponseMessage> likeComment(@PathVariable Long commentId){
        likeService.likeComment(commentId)
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
