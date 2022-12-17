package com.hanghae99.catsanddogs.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long likeCount;

    //수정일

    private String picturePath;

    private CategoryEnum category;

    @OneToMany
    private List<LikePost> likePostList = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();

}