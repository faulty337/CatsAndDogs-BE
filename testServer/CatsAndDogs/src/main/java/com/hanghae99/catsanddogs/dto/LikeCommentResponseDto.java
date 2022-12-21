package com.hanghae99.catsanddogs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeCommentResponseDto {
   private boolean commentLiked;

   public LikeCommentResponseDto(boolean commentLiked){
      this.commentLiked = commentLiked;
   }
}
