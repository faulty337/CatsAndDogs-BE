package com.hanghae99.catsanddogs.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.user.LoginRequestDto;
import com.hanghae99.catsanddogs.dto.user.SignupRequestDto;
import com.hanghae99.catsanddogs.security.jwt.JwtUtil;
import com.hanghae99.catsanddogs.service.KakaoService;
import com.hanghae99.catsanddogs.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }

    /*
    <button id="login-kakao-btn"
    onclick="location.href='https://kauth.kakao.com/oauth/authorize?client_id=7b1771003e7b65a75c3a5baf1255ce8e
    &redirect_uri=http://localhost:8080/api/user/kakao/callback&response_type=code'">

    */
    @GetMapping("/login/kakao")//해당 요청은 프론트에서 직접오는게 아닌 카카오에서 옴
    public ResponseEntity<ResponseMessage> kakaoLogin(@RequestParam String code
            , HttpServletResponse response/*kakao에서 받아오기 때문에 response*/)
            throws JsonProcessingException {

        return kakaoService.kakaoLogin(code, response);
    }

}
