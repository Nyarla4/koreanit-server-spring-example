// package com.koreanit.spring.service;
//
// import com.koreanit.spring.entity.PostEntity;
// import com.koreanit.spring.entity.UserEntity;
// import com.koreanit.spring.repository.HelloRepository;
// import java.util.List;
//
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.stereotype.Service;
//
// @Service
// public class HelloService {
//   private static final int MAX_LIMIT = 1000;
//
//   private final HelloRepository helloRepository;
//     //final: 추후 값을 바꾸지 않을 경우
//
//   public HelloService(HelloRepository helloRepository) {
//     this.helloRepository = helloRepository;
//   }
//
//   public List<UserEntity> users(int limit) {
//       return helloRepository.findUsers(normalizeLimit(limit));
//   }
//
//   public UserEntity user(Long id) {
//     try {//try-catch를 사용하는건 서비스에서만
//         return helloRepository.findUserById(id);
//     } catch (EmptyResultDataAccessException e) {
//         throw new RuntimeException("존재하지 않는 사용자입니다: id=" + id);
//     }
//   }
//
//   public List<PostEntity> posts(int limit) {
//     return helloRepository.findPosts(normalizeLimit(limit));
//   }
//
//   public PostEntity post(Long id) {
//     try {
//         return helloRepository.findPostById(id);
//     } catch (EmptyResultDataAccessException e) {
//         throw new RuntimeException("존재하지 않는 게시글입니다: id=" + id);
//     }
//   }
//
//   private int normalizeLimit(int limit) {
//     if (limit <= 0) {
//         throw new IllegalArgumentException("limit 은 1 이상 입력해주세요");
//     }
//     if (limit > MAX_LIMIT) {
//         return MAX_LIMIT;
//     }
//     return limit;
// }
// }

package com.koreanit.spring.legacy.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.koreanit.spring.common.error.ApiException;
import com.koreanit.spring.common.error.ErrorCode;
import com.koreanit.spring.legacy.repository.HelloRepository;
import com.koreanit.spring.post.Post;
import com.koreanit.spring.post.PostMapper;
import com.koreanit.spring.user.User;
import com.koreanit.spring.user.UserMapper;

@Service
public class HelloService {

  private static final int MAX_LIMIT = 1000;
  //서비스에서는 "어떤 실패"를 "어떤 에러 코드"로 변환할지 처리(컨트롤러에서는 throw 처리X)
  private int normalizeLimit(int limit) {
    if (limit <= 0) {
      throw new ApiException(ErrorCode.INVALID_REQUEST, "limit 은 1 이상 입력해주세요");
    }
    if (limit > MAX_LIMIT) {
      return MAX_LIMIT;
    }
    return limit;
  }

  private final HelloRepository helloRepository;

  public HelloService(HelloRepository helloRepository) {
    this.helloRepository = helloRepository;
  }

  public List<User> users(int limit) {
    int safeLimit = normalizeLimit(limit);
    return UserMapper.toDomainList(helloRepository.findUsers(safeLimit));
  }

  public User user(Long id) {
    try {
      return UserMapper.toDomain(helloRepository.findUserById(id));
    } catch (EmptyResultDataAccessException e) {
      throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE, "존재하지 않는 사용자입니다: id=" + id);
    }
  }

  public List<Post> posts(int limit) {
    int safeLimit = normalizeLimit(limit);
    return PostMapper.toDomainList(helloRepository.findPosts(safeLimit));
  }

  public Post post(Long id) {
    try {
      return PostMapper.toDomain(helloRepository.findPostById(id));
    } catch (EmptyResultDataAccessException e) {
      throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE, "존재하지 않는 게시글입니다: id=" + id);
    }
  }
}