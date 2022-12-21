package com.test.testserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private boolean commentLike;
    private LocalDateTime createAt;
    private Long likeCount;
}
