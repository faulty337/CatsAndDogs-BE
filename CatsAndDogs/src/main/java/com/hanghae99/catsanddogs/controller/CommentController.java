package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.comment.CommentRequestDto;
import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        Long userId = userDetails.getId();
        CommentResponseDto commentResponseDto = commentService.createComment(postId, userId, commentRequestDto.getContent());
        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("댓글 작성 성공", 200, commentResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }


    //응답 예시(ResponseEntity)
//    @GetMapping
//    public ResponseEntity<ResponseMessage> commentTest(){
//        CommentResponseDto commentResponseDto = new CommentResponseDto("댓글");
//        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage("test", 200, commentResponseDto);
//        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
//    }
}
