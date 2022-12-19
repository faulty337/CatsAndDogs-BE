package com.test.testserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@AllArgsConstructor

public class DetailResponseDto {
    private PostResponseDto post;
    private List<CommentResponseDto> comments;
}
