package com.test.testserver.dto;

import com.test.testserver.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String content;
    private long likeCount;
    private boolean postLike;
    private LocalDateTime createAt;
    private LocalDateTime modified;
    private String picturePath;
    private CategoryEnum category;

}
