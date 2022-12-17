package com.hanghae99.catsanddogs.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String email;


}
