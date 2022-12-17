package com.hanghae99.catsanddogs.security.config;


import com.hanghae99.catsanddogs.security.jwt.JwtAuthFilter;
import com.hanghae99.catsanddogs.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    //BCrypt 방식으로 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        //h2 콘솔 사용 설정
        return (web) -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //로그인 방식에 세션 사용하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*
            REST API 서버는 stateless 하게 개발하기 때문에 사용자의 정보를 세션에 저장하지 않는다.
            일반적으로 JWT 같은 토큰을 사용하여 인증하기 때문에 해당 토큰을 Cookie에 저장하지 않는다면
            CSRF 취약점에 대해서는 어느 정도 안전하다고 말할 수 있다.
        * */
        http.csrf().disable();

        http.authorizeRequests()

                /* 유저 로그인 기능 개발 전까지 비활성화

                .antMatchers("/user/**").permitAll()
                .antMatchers("/post").permitAll()
                .antMatchers("/post/{id}").permitAll()

                 */

                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
