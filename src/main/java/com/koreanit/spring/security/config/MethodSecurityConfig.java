package com.koreanit.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity // MethodSecurity 사용 설정용 클래스
public class MethodSecurityConfig {
}

// SpEL(Spring Expression Language) 표현식

// @PreAuthorize("hasRole('ADMIN')")
//    ROLE_ADMIN 권한을 가진 사용자만 허용
//    내부적으로 GrantedAuthority를 검사한다
// @PreAuthorize("#id == 10")
//    #파라미터명 형태로 메서드 인자 참조 가능
//    실제 호출 시 전달된 값으로 비교된다
// @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
//    @빈이름.메서드(인자) 형태로 스프링 빈의 메서드를 SpEL에서 호출한다.
//    복잡한 인가 로직(소유자 검사, DB 조회 포함)을 표현식 문자열 밖(자바 코드)으로 빼는 대표 패턴이다.
//    표현식에는 “규칙 선언”만 남고, 실제 판단 로직은 테스트 가능한 자바 메서드로 이동한다.
//    클래스가 @Service / @Component 로 등록되면 기본 빈 이름은 클래스명의 첫 글자 소문자
//        UserService → userService
//        PostService → postService
// @PreAuthorize("hasRole('ADMIN') or ...")
//    and, or, not 사용 가능