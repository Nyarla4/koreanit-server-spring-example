// package com.koreanit.spring.controller;
//
// // import java.time.LocalDateTime;
// // import java.util.HashMap;
// import java.util.Map;
//
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.koreanit.spring.service.HelloService;
//
// @RestController
// // HTTP 요청 처리
// // 반환값을 응답 데이터로 처리
// // oobject는 json으로 알아서 변환 처리
// public class HelloController {
//
//     private final HelloService helloService;
//     //1. 객체 선언 및 생성자 추가
//     public HelloController(HelloService helloService) {
//         this.helloService = helloService;
//         //beam끼리는 주입방식으로 처리
//     }
//
//     @GetMapping("/hello") // hello 요청이 와서, 그에 맞는 hello() 호출
//     // Controller의 메서드는 public
//     public String hello() {
//         // return "Hello Spring Server : " + LocalDateTime.now().toString();
//         return helloService.helloMessage();
//     }
//
//     @GetMapping("/hello/json")
//     public Map<String, String> helloJson() {
//         // Map<String, String> result = new HashMap<>();
//         // result.put("message", "Hello JSON");
//         // result.put("date", LocalDateTime.now().toString());
//         // return result;
//         return helloService.helloMessageJSON();
//     }
//     // Controller return (객체)
//     // ↓
//     // HttpMessageConverter 선택
//     // ↓
//     // Jackson으로 JSON 직렬화
//     // ↓
//     // HTTP Response Body에 작성
// }

// package com.koreanit.spring.controller;
//
// import com.koreanit.spring.entity.PostEntity;
// import com.koreanit.spring.entity.UserEntity;
// import com.koreanit.spring.service.HelloService;
// import java.util.List;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// public class HelloController {
//
//   private final HelloService helloService;
//
//   public HelloController(HelloService helloService) {
//     this.helloService = helloService;
//   }
//
//   @GetMapping("/hello/users")
//   public List<UserEntity> users(@RequestParam(defaultValue = "1000")int limit) {
//     return helloService.users(limit);
//   }
//
//   @GetMapping("/hello/users/{id}")
//   public UserEntity user(@PathVariable Long id) {
//     return helloService.user(id);
//   }
//
//   @GetMapping("/hello/posts")
//   public List<PostEntity> posts(@RequestParam(defaultValue = "1000")int limit) {
//     return helloService.posts(limit);
//   }
//
//   @GetMapping("/hello/posts/{id}")
//   public PostEntity post(@PathVariable Long id) {
//     return helloService.post(id);
//   }
// }

package com.koreanit.spring.legacy.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.koreanit.spring.common.response.ApiResponse;
import com.koreanit.spring.legacy.service.HelloService;
import com.koreanit.spring.post.PostMapper;
import com.koreanit.spring.post.dto.response.PostResponse;
import com.koreanit.spring.user.UserMapper;
import com.koreanit.spring.user.dto.response.UserResponse;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    // //리턴값을 DTO로 해서 민감한 정보 처리 필요
    // @GetMapping("/hello/users")
    // public List<UserResponse> users(@RequestParam(defaultValue = "1000") int limit) {
    //     return UserMapper.toResponseList(helloService.users(limit));
    // }
//
    // @GetMapping("/hello/users/{id}")
    // public UserResponse user(@PathVariable Long id) {
    //     return UserMapper.toResponse(helloService.user(id));
    // }
//
    // @GetMapping("/hello/posts")
    // public List<PostResponse> posts(@RequestParam(defaultValue = "1000") int limit) {
    //     return PostMapper.toResponseList(helloService.posts(limit));
    // }
//
    // @GetMapping("/hello/posts/{id}")
    // public PostResponse post(@PathVariable Long id) {
    //     return PostMapper.toResponse(helloService.post(id));
    // }
    @GetMapping("/hello/users")
    public ApiResponse<List<UserResponse>> users(@RequestParam(defaultValue = "1000") int limit) {
        return ApiResponse.ok(UserMapper.toResponseList(helloService.users(limit)));
    }

    @GetMapping("/hello/users/{id}")
    public ApiResponse<UserResponse> user(@PathVariable Long id) {
        return ApiResponse.ok(UserMapper.toResponse(helloService.user(id)));
    }

    @GetMapping("/hello/posts")
    public ApiResponse<List<PostResponse>> posts(@RequestParam(defaultValue = "1000") int limit) {
        return ApiResponse.ok(PostMapper.toResponseList(helloService.posts(limit)));
    }

    @GetMapping("/hello/posts/{id}")
    public ApiResponse<PostResponse> post(@PathVariable Long id) {
        return ApiResponse.ok(PostMapper.toResponse(helloService.post(id)));
    }
}