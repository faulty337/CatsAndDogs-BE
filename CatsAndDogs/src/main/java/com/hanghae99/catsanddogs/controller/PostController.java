package com.hanghae99.catsanddogs.controller;


import com.hanghae99.catsanddogs.dto.post.DetailResponseDto;
import com.hanghae99.catsanddogs.dto.post.PostRequestDto;
import com.hanghae99.catsanddogs.dto.post.PostResponseDto;
import com.hanghae99.catsanddogs.dto.post.PostResponseListDto;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        DetailResponseDto detailResponseDto = postService.getPost(postId, userId);
        ResponseMessage<DetailResponseDto> responseMessage = new ResponseMessage<>("조회 성공", 200, detailResponseDto);

        return new ResponseEntity(responseMessage, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity createPost(@RequestPart(value = "file") MultipartFile file,
                                     @RequestPart(value = "requestDto") PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

        PostResponseDto postResponseDto = postService.createPost(requestDto, file, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 작성 성공", 200, postResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }

//    @PutMapping("/{postId}") // 선택 게시글 수정
//    public ResponseEntity updatePost(@PathVariable Long postId,
//                                      @RequestPart(value = "file") MultipartFile file,
//                                      @RequestPart(value = "requestDto") PostRequestDto requestDto,
//                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
//
//        PostResponseDto postResponseDto = postService.updatePost(postId, requestDto, file);
//        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("게시글 수정 성공", 200, postResponseDto);
//        return new ResponseEntity(responseMessage, HttpStatus.OK);
//
//    }




}
