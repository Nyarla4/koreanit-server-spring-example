package com.koreanit.spring.user;

import java.util.List;

public interface UserRepository {

    Long save(String username, String passwordHash, String nickname, String email);

    UserEntity findById(Long id);

    UserEntity findByUsername(String username);

    List<UserEntity> findAll(int limit);

    int updateNickname(Long id, String nickname);

    int updatePassword(Long id, String passwordHash);

    int deleteById(Long id);

    // Users 테이블에 대한 이메일 수정 계약을 정의한다.
    // 구현 방식(SQL, JdbcTemplate 등)은 노출하지 않는다.
    // 반환값(int)은 DB 처리 결과만 전달하며 의미 해석은 하지 않는다.
    int updateEmail(Long id, String email);
}