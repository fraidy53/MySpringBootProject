package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity // 나는 테이블과 매핑되는 클래스다
@Table(name = "customers") // 테이블 이름은 customers
@Getter @Setter
@DynamicUpdate
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // default 값은 auto
    private Long id;

    // JPA(Java Persistence API)에서 엔티티 클래스의 필드를 데이터베이스 컬럼과 매핑할 때 사용하는 어노테이션
    // 데이터 무결성 보장하기 위해 설정
    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerName;


}
