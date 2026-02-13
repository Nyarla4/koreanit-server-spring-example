package com.koreanit.spring.comment.dto.response;

import java.time.LocalDateTime;

import com.koreanit.spring.comment.Comment;

public class CommentResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;    

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // domain -> dto 
    public static CommentResponse from(Comment p) {
        CommentResponse r = new CommentResponse();
        r.id = p.getId();
        r.postId = p.getPostId();
        r.userId = p.getUserId();
        r.content = p.getContent();
        r.createdAt = p.getCreatedAt();
        return r;
    }
}