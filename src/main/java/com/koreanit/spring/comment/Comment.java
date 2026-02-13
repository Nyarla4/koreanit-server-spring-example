package com.koreanit.spring.comment;

import java.time.LocalDateTime;

public class Comment {

  private final Long id;
  private final Long postId;
  private final Long userId;
  private final String content;
  private final LocalDateTime createdAt;

  private Comment(
      Long id,
      Long postId,
      Long userId,
      String content,
      LocalDateTime createdAt) {
    this.id = id;
    this.postId = postId;
    this.userId = userId;
    this.content = content;
    this.createdAt = createdAt;
  }

  public static Comment of(
      Long id,
      Long postId,
      Long userId,
      String content,
      LocalDateTime createdAt) {
    if (id == null)
      throw new IllegalArgumentException("id는 필수입니다");
    
    return new Comment(id, postId, userId, content, createdAt);
  }

  public Long getId() {
    return id;
  }

  public Long getPostId() {
    return postId;
  }

  public Long getUserId() {
    return userId;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}