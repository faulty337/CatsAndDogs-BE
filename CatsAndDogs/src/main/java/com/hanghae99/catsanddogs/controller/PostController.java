package com.hanghae99.catsanddogs.controller;


import com.hanghae99.catsanddogs.dto.PostResponseDto;
import com.hanghae99.catsanddogs.dto.PostResponseListDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity getPostList(){
        Long userId = 1L;
        PostResponseListDto postResponseListDto = postService.getPostList(userId);
        ResponseMessage<PostResponseListDto> responseMessage = new ResponseMessage<>("조회 성공", 200, postResponseListDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        Long userId = 1L;
        PostResponseDto postResponseDto = postService.getPost(postId, userId);
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, postResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }


}
