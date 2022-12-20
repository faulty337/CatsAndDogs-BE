package com.hanghae99.catsanddogs.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.user.LoginRequestDto;
import com.hanghae99.catsanddogs.dto.user.SignupRequestDto;
import com.hanghae99.catsanddogs.service.GoogleService;
import com.hanghae99.catsanddogs.service.KakaoService;
import com.hanghae99.catsanddogs.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }

    @GetMapping("/login/kakao")//해당 요청은 프론트에서 직접오는게 아닌 카카오에서 옴
    public ResponseEntity<ResponseMessage> kakaoLogin(@RequestParam String code
            , HttpServletResponse response/*kakao에서 받아오기 때문에 response*/)
            throws JsonProcessingException {

        return kakaoService.kakaoLogin(code, response);
    }


    @GetMapping(value = "/login/getGoogleURI")
    public @ResponseBody String getGoogleUrl(HttpServletRequest request) throws Exception{
        return googleService.getGoogleUrl();
    }

    @GetMapping("/login/google")
    public ResponseEntity<ResponseMessage> googleLogin(@RequestParam String code
            , HttpServletResponse response)
            throws JsonProcessingException {

        return googleService.googleLogin(code, response);
    }

}
