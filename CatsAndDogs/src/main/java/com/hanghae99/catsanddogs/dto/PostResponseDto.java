package com.hanghae99.catsanddogs.dto;

import com.hanghae99.catsanddogs.entity.CategoryEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private long likeCount;
    private boolean postLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String picturePath;
    private CategoryEnum categoryEnum;
    private List<CommentResponseDto> commentList = new ArrayList<>();


}
