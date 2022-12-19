package com.hanghae99.catsanddogs.dto.post;

import com.hanghae99.catsanddogs.entity.CategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    public String title;
    public String content;
    public String picturePath;
    public String pictureName;
    public CategoryEnum category;



}
