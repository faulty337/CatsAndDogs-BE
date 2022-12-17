package com.hanghae99.catsanddogs.security.jwt;


import com.hanghae99.catsanddogs.security.UserDetails.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "bearer";
    // 60 * 1000L는 1분
    public static final long TOKEN_TIME = (60*60*1000)/*1시간 설정*/ * 3; //3시간 설정

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        //base64로 인코딩이 되어 있기 때문에 secretkey값을 가지고 와서 decode하는 과정
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            //token에 연관이 되지 않는 그냥 string값인 bearer가 6글자 이고 한칸이 띄어져 있는 값을 지운다.
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createToken(String username){
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        //.claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))//토큰의 만료시간 지정
                        .setIssuedAt(date) //이 토큰이 언제 만들어졌는지
                        .signWith(key, signatureAlgorithm)//키 객체와 어떤 알고리즘을 쓸것인지?
                        .compact();
    }

    //token 검증
    public boolean validateToken(String token){
        try {
            //setSigningKey쪽에다가 우리가 token을 만들 때 사용한 키를 넣어주고
            // 어떠한 토큰에 검증을 할건지를 parseClaimsJws()부분에 토큰을 넣어주면 내부적으로 토큰을 검증을 해준다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
