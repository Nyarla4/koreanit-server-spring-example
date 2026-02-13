package com.koreanit.spring.comment;

import java.time.LocalDateTime;

public class CommentEntity {
    private Long id;                  // PK
    private Long postId;              // 글 ID
    private Long userId;              // 작성자 ID (FK)
    private String content;           // 내용
    private LocalDateTime createdAt;  // 작성일

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setPostId(Long postId) { this.postId = postId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}