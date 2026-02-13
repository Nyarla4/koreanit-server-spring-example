package com.koreanit.spring.user;

// 서비스에서는 dto를 알아선 안됨
// import com.koreanit.spring.user.dto.request.UserCreateRequest;
// import com.koreanit.spring.user.dto.request.UserEmailChangeRequest;
// import com.koreanit.spring.user.dto.request.UserPasswordChangeRequest;
// import com.koreanit.spring.user.dto.request.UserNicknameChangeRequest;
import com.koreanit.spring.common.error.*;
// import com.koreanit.spring.user.dto.request.UserEmailChangeRequest;
// import com.koreanit.spring.user.dto.request.UserPasswordChangeRequest;
// import com.koreanit.spring.user.dto.request.UserEmailChangeRequest;
import com.koreanit.spring.security.SecurityUtils;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

// Users 도메인의 정상 흐름 비즈니스 로직을 수행한다.
// Repository 결과(Entity)를 Domain으로 변환한다.
// 비밀번호 해시 생성 및 비교 같은 도메인 규칙을 적용한다.
@Service
public class UserService {

  private static final int MAX_LIMIT = 1000;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  // 의존성은 클래스 단위(메소드에서는X)
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  private int normalizeLimit(int limit) {
    if (limit <= 0) {
      throw new IllegalArgumentException("limit 은 1 이상 입력해주세요");
    }
    return Math.min(limit, MAX_LIMIT);
  }

  public boolean isSelf(Long userId) {//ID 받아서 본인 확인
    Long currentUserId = SecurityUtils.currentUserId();
    return currentUserId != null && userId != null && currentUserId.equals(userId);
  }

  public Long create(String username, String password, String nickname, String email) {

    username = username.trim().toLowerCase();
    nickname = nickname.trim().toLowerCase();

    String normalizedEmail = (email == null) ? null : email.toLowerCase();

    String hash = passwordEncoder.encode(password);

    try {
      return userRepository.save(username, hash, nickname, normalizedEmail);
    } catch (DuplicateKeyException e) {
      throw new ApiException(
          ErrorCode.DUPLICATE_RESOURCE,
          toDuplicateMessage(e));
    }
  }

  //단건 조회: admin 혹은 본인
  @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
  public User get(Long id) {
    try {
      UserEntity e = userRepository.findById(id);
      return UserMapper.toDomain(e);
    } catch (EmptyResultDataAccessException e) {
      throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE, "존재하지 않는 사용자입니다. id=" + id);
    }
  }

  // 유저 리스트: admin일때만
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> list(int limit) {
    int safeLimit = normalizeLimit(limit);
    return UserMapper.toDomainList(userRepository.findAll(safeLimit));
  }

  @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
  public void changeNickname(Long id, String nickname) {
    nickname = nickname.trim().toLowerCase();

    User user = get(id);

    if (user.getNickname().equals(nickname)) {
      return;
    }
    // 3) 실제 변경
    int updated = userRepository.updateNickname(id, nickname);

    if (updated == 0) {
      throw new ApiException(
          ErrorCode.NOT_FOUND_RESOURCE,
          "존재하지 않는 사용자입니다. id=" + id);
    }
  }

  @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
  public void changePassword(Long id, String password) {
    String hash = passwordEncoder.encode(password);

    int updated = userRepository.updatePassword(id, hash);

    if (updated == 0) {
        throw new ApiException(
            ErrorCode.NOT_FOUND_RESOURCE,
            "존재하지 않는 사용자입니다. id=" + id
        );
    }
  }

  @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
  public void delete(Long id) {
    int deleted = userRepository.deleteById(id);

    if (deleted == 0) {
        throw new ApiException(
            ErrorCode.NOT_FOUND_RESOURCE,
            "존재하지 않는 사용자입니다. id=" + id
        );
    }
  }

  public Long login(String username, String password) {
    UserEntity e = userRepository.findByUsername(username);

    boolean ok = passwordEncoder.matches(password, e.getPassword());
    if (!ok) {
      throw new ApiException(ErrorCode.INTERNAL_ERROR, "비밀번호 검증 실패");
    }

    return e.getId();
  }

  @PreAuthorize("hasRole('ADMIN') or @userService.isSelf(#id)")
  // Users 도메인에서 “이메일 변경”이라는 비즈니스 동작을 수행한다.
  // 요청 DTO에서 필요한 값만 추출하여 Repository에 전달한다.
  // 정상 흐름 기준으로 처리하며, 실패 의미 해석은 포함하지 않는다.
  public void changeEmail(Long id, String email) {
    String normalizedEmail = (email == null) ? null : email.toLowerCase();
    try {
      userRepository.updateEmail(id, normalizedEmail);
    } catch (DuplicateKeyException e) {
      throw new ApiException(ErrorCode.DUPLICATE_RESOURCE,
          "이메일 값이 중복되었습니다");
    }
  }

  private String toDuplicateMessage(DuplicateKeyException e) {
    String m = (e.getMessage() == null) ? "" : e.getMessage();

    // MySQL 기준: "Duplicate entry ... for key '...'"
    // DB/드라이버에 따라 메시지 포맷은 달라질 수 있으므로
    // key 이름 기반으로만 판단한다.
    if (m.contains("for key") && (m.contains("users.username") || m.contains("'username'") || m.contains("username"))) {
      return "이미 존재하는 username입니다";
    }
    if (m.contains("for key") && (m.contains("users.email") || m.contains("'email'") || m.contains("email"))) {
      return "이미 존재하는 email입니다";
    }

    return "이미 존재하는 값입니다";
  }
}