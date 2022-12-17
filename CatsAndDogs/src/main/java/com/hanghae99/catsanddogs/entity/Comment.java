package com.hanghae99.catsanddogs.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long likeCount;

    @OneToMany
    @JoinColumn(name = "commentId")
    private List<LikeComment> likeCommentList = new ArrayList<>();

    @ManyToOne
    private User user;


}