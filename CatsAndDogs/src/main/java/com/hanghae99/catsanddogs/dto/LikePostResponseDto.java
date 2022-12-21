package com.hanghae99.catsanddogs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikePostResponseDto {
    private boolean postLiked;

    private Long likeCount;


    public LikePostResponseDto(boolean postLiked, Long likeCount) {
        this.postLiked = postLiked;
        this.likeCount = likeCount;
    }
}



