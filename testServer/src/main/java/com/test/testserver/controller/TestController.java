package com.test.testserver.controller;

import com.test.testserver.dto.*;
import com.test.testserver.entity.CategoryEnum;
import com.test.testserver.exception.CustomException;
import com.test.testserver.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class TestController {
    static long localId= 0;

    @GetMapping
    public ResponseEntity getPostList(){
        PostResponseListDto postResponseListDto = new PostResponseListDto();
        PostResponseDto postResponseDto;
        for(int i = 1; i <= 10; i++){
            postResponseDto = new PostResponseDto((long)i, "제목"+i, "범준", "내용" + i, 10+i, i%3==0, LocalDateTime.now(), LocalDateTime.now(), "경로", CategoryEnum.CAT);
            postResponseListDto.addPostResponseDto(postResponseDto);
        }
        TodaysPetDto todaysPetDto = new TodaysPetDto();
        todaysPetDto.setFirst(postResponseListDto.getPostResponseDto(1));
        todaysPetDto.setSecond(postResponseListDto.getPostResponseDto(2));

        MainDto mainDto = new MainDto();
        mainDto.setPostResponseListDto(postResponseListDto);
        mainDto.setTodaysPetDto(todaysPetDto);

        ResponseMessage<MainDto> responseMessage = new ResponseMessage<MainDto>("전체 조회 성공", 200, mainDto);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        if(postId.equals(4L)){
            return new ResponseEntity( new ResponseMessage( new CustomException(ErrorCode.CONTENT_NOT_FOUND).getErrorCode().getMsg(), new CustomException(ErrorCode.CONTENT_NOT_FOUND).getErrorCode().getStatusCode(), new CustomException(ErrorCode.CONTENT_NOT_FOUND).getErrorCode())
                    , HttpStatus.valueOf(new CustomException(ErrorCode.CONTENT_NOT_FOUND).getErrorCode().getStatusCode()));
        }
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(int i = 1; i < postId+2; i++){
            commentResponseDtoList.add(new CommentResponseDto((long)i, "댓글내용"+i, "범준", i%3==0, LocalDateTime.now(), (long)3*i));
        }

        PostResponseDto postResponseDto = new PostResponseDto(postId, "제목"+postId, "범준", "내용"+ postId,  3*postId, postId%3==0, LocalDateTime.now(), LocalDateTime.now(), "경로", CategoryEnum.CAT);
        DetailResponseDto detailResponseDto = new DetailResponseDto(postResponseDto, commentResponseDtoList);
        ResponseMessage<DetailResponseDto> responseMessage = new ResponseMessage<DetailResponseDto>("게시물 조회 성공", 200, detailResponseDto);
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity getPost(@RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = new PostResponseDto(localId++, postRequestDto.getTitle(), "범준", postRequestDto.getContent(), 0, false, LocalDateTime.now(), LocalDateTime.now(), "대충 그럴싸한 경로", postRequestDto.getCategory());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<PostResponseDto>("요청 성공", 200, postResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);



    }


}
