package com.hanghae99.catsanddogs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeCommentResponseDto {
   private boolean commentLiked;
   private long likeCount;

   public LikeCommentResponseDto(boolean commentLiked, Long likeCount){

      this.commentLiked = commentLiked;
      this.likeCount = likeCount;
   }

}
