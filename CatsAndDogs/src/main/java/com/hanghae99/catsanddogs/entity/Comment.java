package com.hanghae99.catsanddogs.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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


    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }
    public Comment(String content, User user) {
        this.content = content;
        this.user = user;
    }
}

