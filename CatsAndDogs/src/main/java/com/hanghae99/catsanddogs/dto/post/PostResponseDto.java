package com.hanghae99.catsanddogs.dto.post;

import com.hanghae99.catsanddogs.dto.comment.CommentResponseDto;
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
    private String nickname;
    private long id;
    private String title;
    private String content;
    private long likeCount;
    private boolean postLike;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String pictureName;
    private CategoryEnum category;


    public PostResponseDto(Post post, Long userId){
        this.id = post.getId();
        this.title = post.getTitle();
        this.nickname = post.getUsers().getNickname();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.postLike = post.getLikePostList().stream().anyMatch(likePost -> likePost.getUserId().equals(userId));

        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.pictureName = post.getPictureName();
        this.category = post.getCategory();

    }


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.pictureName = post.getPictureName();
        this.category = post.getCategory();
        this.nickname = post.getUsers().getNickname();
    }
}
