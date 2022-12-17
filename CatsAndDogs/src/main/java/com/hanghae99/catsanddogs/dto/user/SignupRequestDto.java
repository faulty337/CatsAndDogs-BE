package com.hanghae99.catsanddogs.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    private String username;//유저의 아이디

    private String password;

    private String nickname;

    private String email;

}
