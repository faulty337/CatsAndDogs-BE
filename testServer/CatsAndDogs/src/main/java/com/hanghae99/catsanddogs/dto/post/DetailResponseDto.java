package com.hanghae99.catsanddogs.dto.post;

import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
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
