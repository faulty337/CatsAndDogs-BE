package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CommentController {


    //응답 예시(ResponseEntity)
    @GetMapping
    public ResponseEntity<ResponseMessage> commentTest(){
        CommentResponseDto commentResponseDto = new CommentResponseDto("댓글");
        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage("test", 200, commentResponseDto);
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
    }
}
