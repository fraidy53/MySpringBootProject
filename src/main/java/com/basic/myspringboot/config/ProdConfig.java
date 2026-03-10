package com.basic.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration

public class ProdConfig {
    @Bean
    public CustomerVO customerVO(){
        return CustomerVO.builder() // CustomerVOBuilder 리턴 타입
                .mode("운영 환경")
                .rate(1.5)
                .build(); // CustomerVO 리턴 타입
        }
}
