package com.hanghae99.catsanddogs.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder//entity의 개수가 너무 많아질 때를 대비해서 Builder패턴 적용
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private SocialEnum social;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    //기존 회원이 확인되면 카카오 아이디 업데이트
    public User socialUpdate(SocialEnum social) {
        this.social = social;
        return this;
    }

}

