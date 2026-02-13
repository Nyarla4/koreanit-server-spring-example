package com.koreanit.spring.user.dto.request;

// 이메일 변경 요청 바디(JSON)를 표현한다.
// 외부에서 전달되는 이메일 값을 그대로 수용한다.
// 검증(@Email) 여부와 무관하게 요청 계약 구조를 고정하는 역할만 담당한다.
public class UserEmailChangeRequest {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}