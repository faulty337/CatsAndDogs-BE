package com.hanghae99.catsanddogs.entity;

import com.hanghae99.catsanddogs.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;


    private long likeCount;

    private String nickname;



    //수정일

    private String picturePath; // 사진 저장 경로

    private String pictureName;


    private CategoryEnum category;

    @OneToMany
    @JoinColumn(name = "postId")
    private List<LikePost> likePostList = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "postId")
    private List<Comment> commentList = new ArrayList<>();


    public Post(String title, String content, String picturePath, CategoryEnum category) {
        this.title = title;
        this.content = content;
        this.picturePath = picturePath;
        this.category = category;
    }


    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }


    public Post(PostRequestDto requestDto, String nickname, String picturePath, String pictureName) {

        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.nickname = nickname;
        this.picturePath = picturePath;
        this.pictureName = pictureName;

    }

    public void update(PostRequestDto requestDto, String picturePath, String pictureName) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.picturePath = picturePath;
        this.pictureName = pictureName;


    }



    public void setLikecount(long likeCount) {
        this.likeCount = likeCount;
    }
}

