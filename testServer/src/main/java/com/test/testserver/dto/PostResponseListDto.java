package com.test.testserver.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseListDto {
    private List<PostResponseDto> postResponseDtoList = new ArrayList<>();

    public void addPostResponseDto(PostResponseDto postResponseDto){
        this.postResponseDtoList.add(postResponseDto);
    }
    public PostResponseDto getPostResponseDto(int index){
        return postResponseDtoList.get(index);
    }
}
