package com.basic.myspringboot.security.userinfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {
    // UserInfo 엔티티의 정보를 UserDetails 인터페이스에 맞게 변환하여 Spring Security에서 사용할 수 있도록 하는 클래스
    private String email;
    private String password;
    private List<GrantedAuthority> authorities; // 엔티티에서 권한의 정보
    private UserInfo userInfo;

    public UserInfoUserDetails(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.email=userInfo.getEmail();
        this.password=userInfo.getPassword();
        // roles : ROLE_USER,ROLE_ADMIN
        this.authorities= Arrays.stream(userInfo.getRoles().split(","))
                .map(roleName -> new SimpleGrantedAuthority(roleName)) // 람다식
                // .map : 스트림의 각 요소에 대해 주어진 함수를 적용하여 새로운 스트림을 생성하는 메서드
                // .map(SimpleGrantedAuthority::new)
                // .collect : 스트림의 요소들을 수집하여 리스트, 세트, 맵 등의 컬렉션으로 변환하는 메서드
                // Stream<SimpleGrantedAuthority> => List<SimpleGrantedAuthority>
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /*
        getUsername과 getPassword 메서드는
        AuthenticationManager가 인증처리를 할 때 호출됨
    */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }    

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}