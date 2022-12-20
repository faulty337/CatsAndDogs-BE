package com.hanghae99.catsanddogs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikePostResponseDto {
    private boolean postLiked;

    public LikePostResponseDto(boolean postLiked){
        this.postLiked = postLiked;
    }
}
