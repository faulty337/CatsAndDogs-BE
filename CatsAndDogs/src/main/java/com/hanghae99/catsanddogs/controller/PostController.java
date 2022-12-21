package com.hanghae99.catsanddogs.controller;


import com.hanghae99.catsanddogs.dto.post.*;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "전체 게시글 조회")
    @GetMapping
    public ResponseEntity getPostList(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails == null ? -1 : userDetails.getId();
        MainResponseDto mainResponseDto = postService.getPostList(userId);
        ResponseMessage<MainResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, mainResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));

    }

    @ApiOperation(value = "게시물 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity getPost(
            @PathVariable Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails
){
        Long userId = userDetails == null ? -1 : userDetails.getId();
        DetailResponseDto detailResponseDto = postService.getPost(postId, userId);
        ResponseMessage<DetailResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, detailResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));

    }

    @ApiOperation(value = "게시물 작성")
    @PostMapping
    public ResponseEntity createPost(
                                     @RequestBody PostRequestDto requestDto,
                                     @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        PostResponseDto postResponseDto = postService.createPost(requestDto, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 작성 성공", 200, postResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }


    @ApiOperation(value = "게시물 수정")
    @PutMapping("/{postId}") // 선택 게시글 수정
    public ResponseEntity updatePost(@PathVariable Long postId,
                                     @RequestBody PostRequestDto requestDto,
                                     @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        PostResponseDto postResponseDto = postService.updatePost(postId, requestDto, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 수정 성공", 200, postResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));

    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("/{postId}") // 선택 게시글 삭제
    public ResponseEntity deletePost(@PathVariable Long postId,
                                     @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        postService.deletePost(postId, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 삭제 성공", 200, null);
        return new ResponseEntity(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));

    }




}