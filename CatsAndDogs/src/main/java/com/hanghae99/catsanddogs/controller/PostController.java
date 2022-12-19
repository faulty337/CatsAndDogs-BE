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
    public ResponseEntity getPostList(){
        Long userId = 1L;
        MainResponseDto mainResponseDto = postService.getPostList(userId);
        ResponseMessage<MainResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, mainResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }

    @ApiOperation(value = "게시물 상세 조회")
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        Long userId = 1L;
        DetailResponseDto detailResponseDto = postService.getPost(postId, userId);
        ResponseMessage<DetailResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, detailResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }

    @ApiOperation(value = "게시물 작성")
    @PostMapping
    public ResponseEntity createPost(@RequestPart(value = "file") MultipartFile file,
                                     @RequestPart(value = "requestDto") PostRequestDto requestDto,
                                     @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        PostResponseDto postResponseDto = postService.createPost(requestDto, file, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 작성 성공", 200, postResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }


    @ApiOperation(value = "게시물 수정")
    @PutMapping("/{postId}") // 선택 게시글 수정
    public ResponseEntity updatePost(@PathVariable Long postId,
                                     @RequestPart(value = "file") MultipartFile file,
                                     @RequestPart(value = "requestDto") PostRequestDto requestDto,
                                     @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        PostResponseDto postResponseDto = postService.updatePost(postId, requestDto, file, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 수정 성공", 200, postResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }




}
