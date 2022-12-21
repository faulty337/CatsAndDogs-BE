package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.comment.CommentRequestDto;
import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
class CommentController {
    private final CommentService commentService;
    @ApiOperation(value = "댓글 작성")
    @PostMapping("/{postId}/comment")
    public ResponseEntity createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        Long userId = userDetails.getId();
        CommentResponseDto commentResponseDto = commentService.createComment(postId, userId, commentRequestDto.getContent());
        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("댓글 작성 성공", 200, commentResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long userId = userDetails.getId();
        commentService.deleteComment(commentId, userId, postId);
        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("댓글 삭제 성공", 200, null);

        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}
