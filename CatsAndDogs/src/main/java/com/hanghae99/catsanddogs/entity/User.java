package com.hanghae99.catsanddogs.entity;
import lombok.*;

import javax.persistence.*;

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

    private String username;

    private String password;

    private String nickname;

    private String email;

}
