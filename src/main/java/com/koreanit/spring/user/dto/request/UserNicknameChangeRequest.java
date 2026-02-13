package com.koreanit.spring.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 비밀번호 변경 요청 데이터를 전달한다.
// 실제 암호화 및 저장은 Service 계층에서 수행된다.
public class UserNicknameChangeRequest {

    @NotBlank(message = "nickname은 필수입니다")
    @Size(min = 2, max = 20, message = "nickname은 2~20자여야 합니다")
    @Pattern(regexp = "^[^\\s]+$", message = "nickname에는 공백을 포함할 수 없습니다")
    private String nickname;

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}