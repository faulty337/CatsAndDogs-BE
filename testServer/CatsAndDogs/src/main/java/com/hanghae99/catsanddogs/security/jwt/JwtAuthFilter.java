package com.hanghae99.catsanddogs.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae99.catsanddogs.dto.ResponseMessage;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = jwtUtil.resolveToken(request);

        //토큰이 request header에 있는지 없는지 확인.- 모든 URI가 permitAll이 아니기 때문
        if(token != null) {
            if(!jwtUtil.validateToken(token)){
                jwtExceptionHandler(response, "로그인 상태를 확인해주세요.", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token); //토큰에 이상이 없다면 토큰에서 유저의 정보를 가지고 옴.(Claims 객체에다가)
            setAuthentication(info.getSubject()); //setAuthentication함수로 정보의 Subject안에 들어있는 (우리는 username을 넣어놓음) 유저네임을 받는다.
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context); //인증 객체가 들어옴.
    }//이제 다음 필터로 이동했을 때 이 요청은 인증이 되었구나라고 Security에서 인지하고 Controller쪽으로 요청이 넘어감.

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        //클라이언트로 반환하는 부분.
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new ResponseMessage<String>(msg, statusCode, "error"));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
