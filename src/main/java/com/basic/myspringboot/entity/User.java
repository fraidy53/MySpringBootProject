package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// DB 테이블 한 행(row)을 자바 객체로 매핑하는 클래스. 즉, users 테이블 구조를 코드로 표현한 설계도
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name은 필수 입력 항목입니다.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email은 필수 입력 항목입니다.")
    @Email // 이메일 형식 검증
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

}