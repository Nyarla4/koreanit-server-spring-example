package com.koreanit.spring.comment;

import com.koreanit.spring.comment.dto.request.CommentCreateRequest;
import com.koreanit.spring.comment.dto.response.CommentResponse;
import com.koreanit.spring.common.response.ApiResponse;
import com.koreanit.spring.security.SecurityUtils;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/posts")
public class CommentController {

  private final CommentService commentService;
    
  public CommentController(CommentService commentService) {
      this.commentService = commentService;
  }

  //@CommentMapping()
  @PostMapping("/api/posts/{postId}/comments")
  public ApiResponse<CommentResponse> create(
    @PathVariable long postId,
    @RequestBody @Valid CommentCreateRequest req) {
      Long userId = SecurityUtils.currentUserId();
      Comment p = commentService.create(userId, postId, req.getContent());
      return ApiResponse.ok(CommentMapper.toResponse(p));
  }

  @GetMapping("/api/posts/{postId}/comments")
  public ApiResponse<List<CommentResponse>> list(
      @PathVariable long postId,
    @RequestParam(defaultValue = "20") int limit) {
    List<Comment> comments = commentService.list(postId, limit);
    return ApiResponse.ok(CommentMapper.toResponseList(comments));
  }

  @DeleteMapping("/api/comments/{id}")
  public ApiResponse<Void> delete(@PathVariable long id) {
      commentService.delete(id);
      return ApiResponse.ok();
  }
}