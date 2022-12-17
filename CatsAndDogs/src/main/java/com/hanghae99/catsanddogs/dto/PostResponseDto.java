package com.hanghae99.catsanddogs.dto;

import com.hanghae99.catsanddogs.entity.CategoryEnum;
import com.hanghae99.catsanddogs.entity.LikePost;
import com.hanghae99.catsanddogs.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private long likeCount;
    private boolean postLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String picturePath;
    private CategoryEnum category;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post, Long userId){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.postLike = post.getLikePostList().stream().anyMatch(likePost -> likePost.getUserId().equals(userId));
//        for(LikePost likePost : post.getLikePostList()){
//            if(likePost.getUserId().equals(userId)){
//                this.postLike = true;
//                break;
//            }
//        }
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.picturePath = post.getPicturePath();
        this.category = post.getCategory();
        this.commentList = post.getCommentList().stream().map(comment -> new CommentResponseDto(comment, userId)).collect(Collectors.toList());

    }


}
