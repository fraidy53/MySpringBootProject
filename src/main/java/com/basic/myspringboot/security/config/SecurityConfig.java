package com.basic.myspringboot.security.config;

import com.basic.myspringboot.security.userinfo.UserInfoUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Spring Security 설정을 활성화하는 어노테이션
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
        // 해시 함수를 사용하는 암호화 방식으로, BCrypt는 강력한 보안성을 제공하여 비밀번호를 안전하게 저장할 수 있도록 도와줌
    }

    // @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        // 사용자 정보를 메모리에 저장하는 UserDetailsService 빈 등록
//        UserDetails admin = User.withUsername("adminboot")
//                .password(encoder.encode("pwd1"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("userboot")
//                .password(encoder.encode("pwd2"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//        // 메모리 기반의 UserDetailsService 구현체로,
//        // 애플리케이션이 실행되는 동안 사용자 정보를 메모리에 저장하여 인증에 사용
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // DaoAuthenticationProvider는 Spring Security에서 제공하는 AuthenticationProvider 구현체로,
        // 사용자 인증을 처리하는 데 사용됩니다. 이 클래스는 UserDetailsService와 PasswordEncoder를 사용하여 인증을 수행
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Spring Security에서 보안 규칙을 설정하는 코드
    @Bean // Spring Boot 컨테이너에 SecurityFilterChian 객체 등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // csrf 보안 기능 끄기, csrf: 다른 사이트가 내 계정으로 요청 보내는 공격
                .authorizeHttpRequests(auth -> { // 요청 권한 설정
                    // /api/users/welcome 엔드포인트는 모든 사용자에게 허용(인증 필요 없음)
                    auth.requestMatchers("/api/users/welcome", "/userinfos/new").permitAll()
                            //  /users/** 엔드포인트는 인증된 사용자만 접근 허용(인증 필수!!)
                            .requestMatchers("/api/users/**").authenticated();
                })
                // spring이 제공하는 login form을 사용하여 인증 처리,
                // withDefaults()는 기본 설정을 사용하겠다는 의미
                .formLogin(withDefaults())
                .build(); // 지금까지 만든 보안 설정을 SecurityFilterChain 객체로 빌드하여 반환
    }
}
