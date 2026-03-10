package com.basic.myspringboot.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter // Getter 어노테이션을 사용하여 클래스의 모든 필드에 대한 getter 메서드를 자동으로 생성
@Builder // 빌더 패턴을 사용하여 객체를 생성하기 위해 사용
@ToString // 객체 정보를 문자열로 출력하기 위해 사용

public class CustomerVO {
    private String mode;
    private double rate;
}
