package com.koreanit.spring.user;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.koreanit.spring.common.response.ApiResponse;
import com.koreanit.spring.user.dto.request.UserCreateRequest;
import com.koreanit.spring.user.dto.request.UserEmailChangeRequest;
import com.koreanit.spring.user.dto.request.UserPasswordChangeRequest;
import com.koreanit.spring.user.dto.request.UserNicknameChangeRequest;
import com.koreanit.spring.user.dto.response.UserResponse;

import jakarta.validation.Valid;

// Users 리소스에 대한 CRUD API를 제공한다.
// Service 결과(Domain)를 DTO로 변환하여 반환한다.
// 모든 응답을 ApiResponse로 통일한다.
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<Long> create(@Valid @RequestBody UserCreateRequest req) {
        return ApiResponse
                .ok(userService.create(req.getUsername(), req.getPassword(), req.getNickname(), req.getEmail()));
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(UserMapper.toResponse(userService.get(id)));
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> list(@RequestParam(defaultValue = "1000") int limit) {
        return ApiResponse.ok(UserMapper.toResponseList(userService.list(limit)));
    }

    @PutMapping("/{id}/nickname")
    public ApiResponse<Void> changeNickname(@PathVariable Long id, @Valid @RequestBody UserNicknameChangeRequest req) {
        userService.changeNickname(id, req.getNickname());
        return ApiResponse.ok();
    }

    @PutMapping("/{id}/password")
    public ApiResponse<Void> changePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordChangeRequest req) {
        userService.changePassword(id, req.getPassword());
        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ApiResponse.ok();
    }

//  이메일 변경에 대한 HTTP API 엔드포인트를 추가한다.
// 요청 DTO를 Service로 전달하고, 결과를 ApiResponse로 감싼다.
// 비즈니스 판단이나 DB 처리 로직은 포함하지 않는다.
    @PutMapping("/{id}/email")
    public ApiResponse<Void> changeEmail(
            @PathVariable Long id,
            @RequestBody UserEmailChangeRequest req) {
        userService.changeEmail(id, req.getEmail());
        return ApiResponse.ok();
    }
}