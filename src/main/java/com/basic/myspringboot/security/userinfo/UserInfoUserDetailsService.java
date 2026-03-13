package com.basic.myspringboot.security.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optionalUserInfo = repository.findByEmail(username); // 이메일로 사용자 조회
        // Optional을 사용하여 사용자 정보가 존재하는지 확인하고, 존재하면 UserInfoUserDetails 객체로 변환하여 반환
        // 입력(T) Entity -> U: UserInfoUserDetails(=UserDetails)
        // .map() : Optional이 가지고 있는 값을 변환하는 메서드, UserInfo 객체를 UserInfoUserDetails 객체로 변환
        return optionalUserInfo.map(userInfo -> new UserInfoUserDetails(userInfo))
                //userInfo.map(UserInfoUserDetails::new)
                // .orElseThrow() : Optional이 비어있을 때 예외를 던지는 메서드, 사용자 정보가 존재하지 않을 때 UsernameNotFoundException 예외를 던짐
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo savedUserInfo = repository.save(userInfo);
        return savedUserInfo.getName() + " user added!!";
    }
}