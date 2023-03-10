package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.dto.ResponseMessage;
import com.hanghae99.catsanddogs.dto.user.LoginRequestDto;
import com.hanghae99.catsanddogs.dto.user.SignupRequestDto;
import com.hanghae99.catsanddogs.entity.SocialEnum;
import com.hanghae99.catsanddogs.entity.User;
import com.hanghae99.catsanddogs.exception.CustomException;
import com.hanghae99.catsanddogs.exception.ErrorCode;
import com.hanghae99.catsanddogs.repository.UserRepository;
import com.hanghae99.catsanddogs.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ResponseMessage> signup(SignupRequestDto signupRequestDto){

        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Pattern userPattern = Pattern.compile("^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,10}$");
        Pattern pwPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");


        if(!userPattern.matcher(username).find()){
            throw new CustomException(ErrorCode.INVALID_USERNAME_PATTERN);
        }

        if(!pwPattern.matcher(signupRequestDto.getPassword()).find()){
            throw new CustomException(ErrorCode.INVALID_PASSWORD_PATTERN);
        }


        Optional<User> checkUser = userRepository.findByUsername(username);
        if (checkUser.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        Optional<User> checkEmail = userRepository.findByEmail(signupRequestDto.getEmail());
        if (checkEmail.isPresent()) {
            if(checkEmail.get().getSocial() != null){
                if(checkEmail.get().getSocial() == SocialEnum.KAKAO) {
                    throw new CustomException(ErrorCode.DUPLICATE_KAKAO_EMAIL);
                }else if(checkEmail.get().getSocial() == SocialEnum.GOOGLE){
                    throw new CustomException(ErrorCode.DUPLICATE_GOOGLE_EMAIL);
                }
            }
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        Optional<User> checkNickname = userRepository.findByNickname(signupRequestDto.getNickname());
        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME);
        }

        if(signupRequestDto.getEmail()==null || signupRequestDto.getNickname()==null){
            throw new CustomException(ErrorCode.REQUIRED_ALL);
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(password)
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail()).build();
        userRepository.save(user);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("???????????? ??????", 200, null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseMessage> login(LoginRequestDto loginRequestDto,  HttpServletResponse response){

        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new CustomException(ErrorCode.USERNAME_NOT_FOUND)
        );

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }
        String token = jwtUtil.createToken(user.getUsername());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("????????? ??????", 200, token), HttpStatus.OK);
    }
}
