package com.koreanit.spring.security;

import org.springframework.web.bind.annotation.*;
import com.koreanit.spring.common.response.ApiResponse;
import com.koreanit.spring.user.dto.request.UserLoginRequest;
import com.koreanit.spring.user.dto.response.UserResponse;
import com.koreanit.spring.user.UserMapper;
import com.koreanit.spring.user.UserService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AuthController {

    public static final String SESSION_USER_ID = "LOGIN_USER_ID";

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<Long> login(@RequestBody UserLoginRequest req, HttpSession session) {
      Long userId = userService.login(req.getUsername(), req.getPassword());
      session.setAttribute(SESSION_USER_ID, userId);// session에 추가하는 위치
      return ApiResponse.ok(userId);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.ok();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        return ApiResponse.ok(UserMapper.toResponse(userService.get(userId)));
    }
}