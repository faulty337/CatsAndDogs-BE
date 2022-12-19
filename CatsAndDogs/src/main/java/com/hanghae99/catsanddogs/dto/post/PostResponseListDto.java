package com.hanghae99.catsanddogs.dto.post;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseListDto {
    List<PostResponseDto> postResponseDtoList = new ArrayList<>();

    public void addPostResponseDto(PostResponseDto postResponseDto){
        this.postResponseDtoList.add(postResponseDto);
    }
}
