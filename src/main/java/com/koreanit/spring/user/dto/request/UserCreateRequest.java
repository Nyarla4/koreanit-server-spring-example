package com.koreanit.spring.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 회원가입 요청에 필요한 입력 값을 정의한다.
// 요청 값에 대한 필수 조건, 길이 제한, 형식 판단 규칙을 선언한다.
// 선택 값(email)에 대해 입력 표준화를 수행한다.

// username, password, nickname
//     필수
//     길이 제한
// email
//     선택 값
//     형식만 판단
public class UserCreateRequest {

    @NotBlank(message = "username은 필수입니다")
    @Size(min = 4, max = 20, message = "username은 4~20자여야 합니다")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "username은 영문자와 숫자만 사용할 수 있습니다")
                // 정규 표현식 처리로 조건 바꿀 수 있음
    private String username;

    @NotBlank(message = "password는 필수입니다")
    @Size(min = 4, max = 50, message = "password는 4~50자여야 합니다")
    private String password;

    @NotBlank(message = "nickname은 필수입니다")
    @Size(min = 2, max = 20, message = "nickname은 2~20자여야 합니다")
    @Pattern(regexp = "^[^\\s]+$", message = "nickname에는 공백을 포함할 수 없습니다")
    private String nickname;

    @Email(message = "email 형식이 올바르지 않습니다")
    private String email;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null) {
            this.email = null;
            return;
        }
        String v = email.trim();
        this.email = v.isEmpty() ? null : v;
    }
}