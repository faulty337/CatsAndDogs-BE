package com.hanghae99.catsanddogs.security.UserDetails;

import com.hanghae99.catsanddogs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    private final String username;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//    admin권한 기능이 없으므로 임시적으로 비활성화
//        UserRoleEnum role = user.getRole();
//        String authority = role.getAuthority();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("user"/*임시적으로 유저로 고정*/);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername(){
        return this.username;
    }


    @Override
    public String getPassword() {
        return null; //패스워드를 가져다 쓸 일이 없음으로 null 고정
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
