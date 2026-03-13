// 엔티티
package com.basic.myspringboot.security.userinfo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;
}