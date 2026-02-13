package com.koreanit.spring.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 보안/개체/컨피그
*/
//서버 전역에서 사용할 PasswordEncoder를 공통 Bean으로 등록한다.
//비밀번호 해시 정책(BCrypt)을 단일 지점에서 고정한다.
//인증/인가 로직과는 무관한 암호화 정책 선언만을 담당한다.
@Configuration
public class SecurityBeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}