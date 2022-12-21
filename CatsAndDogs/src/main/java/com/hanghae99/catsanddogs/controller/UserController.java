package com.hanghae99.catsanddogs.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.user.LoginRequestDto;
import com.hanghae99.catsanddogs.dto.user.SignupRequestDto;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsImpl;
import com.hanghae99.catsanddogs.service.GoogleService;
import com.hanghae99.catsanddogs.service.KakaoService;
import com.hanghae99.catsanddogs.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/login/kakao")
    public ResponseEntity<ResponseMessage> kakaoLogin(@RequestParam String code
            , HttpServletResponse response)
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

    @GetMapping
    public ResponseEntity<ResponseMessage> getNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        if(userDetails == null){
            throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
        }
        ResponseMessage<?> responseMessage = new ResponseMessage<>("요청 성공", 200, userDetails.getNickname());
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

}
