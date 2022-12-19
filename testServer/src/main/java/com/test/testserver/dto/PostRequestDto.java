package com.test.testserver.dto;

import com.test.testserver.entity.CategoryEnum;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private CategoryEnum category;
    private String picturePath;
}
