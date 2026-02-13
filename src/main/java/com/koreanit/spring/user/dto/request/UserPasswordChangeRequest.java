package com.koreanit.spring.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 비밀번호 변경 요청에 필요한 입력 값만을 정의한다.
// 비밀번호에 대한 필수 조건과 길이 제한을 고정한다.
// 비즈니스 규칙(암호화 등)은 포함하지 않는다.
public class UserPasswordChangeRequest {

    @NotBlank(message = "password는 필수입니다")
    @Size(min = 4, max = 50, message = "password는 4~50자여야 합니다")
    @Pattern(regexp = "^[^\\s]+$", message = "password에는 공백을 포함할 수 없습니다")
    private String password;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}