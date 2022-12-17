package com.hanghae99.catsanddogs.dto.comment;


import com.hanghae99.catsanddogs.entity.Comment;
import com.hanghae99.catsanddogs.entity.LikeComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private boolean commentLike;
    private LocalDateTime createdAt;
    private long likeCount;

    public CommentResponseDto(Comment comment, Long userId){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.commentLike = comment.getLikeCommentList().stream().anyMatch(likeComment -> likeComment.getUserId().equals(userId));
//        for(LikeComment likeComment : comment.getLikeCommentList()){
//            if(likeComment.getUserId().equals(userId)){
//                this.commentLike = true;
//                break;
//            }
//        }
        this.createdAt = comment.getCreatedAt();
        this.likeCount = comment.getLikeCount();
    }
}
