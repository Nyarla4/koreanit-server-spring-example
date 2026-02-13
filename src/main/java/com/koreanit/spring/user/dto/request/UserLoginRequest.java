package com.koreanit.spring.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 로그인 요청에 필요한 사용자 식별 정보와 인증 정보를 정의한다.
// 최소한의 필수 조건과 길이 제한만을 판단한다.
// 인증 성공 여부 판단은 Service 또는 Security 계층에서 수행한다.
public class UserLoginRequest {

    @NotBlank(message = "username은 필수입니다")
    @Size(min = 4, max = 20, message = "username은 4~20자여야 합니다")
    @Pattern(regexp = "^[^\\s]+$", message = "username에는 공백을 포함할 수 없습니다")
    private String username;

    @NotBlank(message = "password는 필수입니다")
    @Size(min = 4, max = 50, message = "password는 4~50자여야 합니다")
    @Pattern(regexp = "^[^\\s]+$", message = "password에는 공백을 포함할 수 없습니다")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}