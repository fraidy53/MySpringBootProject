package com.basic.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootApplication.class, args);
	}

    // 이 자체가 configuration이자 bean입니다. @Bean이 붙은 메서드의 리턴값이 bean으로 등록
    // 전략3에서 @ComponentScan & @Bean 역할을 어플리케이션 클래스가 담당(Boot는 자동으로 이런 역할 함)
    @Bean
    public String hello(){
        System.out.println("====Spring Bean입니다. =====");
        return "Hello Bean";
    }
}
