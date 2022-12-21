package com.hanghae99.catsanddogs.dto.post;

import com.hanghae99.catsanddogs.entity.CategoryEnum;
import com.hanghae99.catsanddogs.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    public String title;
    public String content;
    public String pictureName;
    public CategoryEnum category;
}
