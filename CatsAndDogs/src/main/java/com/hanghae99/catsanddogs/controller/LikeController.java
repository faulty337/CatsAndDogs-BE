package com.hanghae99.catsanddogs.controller;

import com.hanghae99.catsanddogs.dto.CommentResponseDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/like")
@RestController
public class LikeController {
    @GetMapping
    public ResponseEntity<ResponseMessage> commentLike(){
       
    }
}
