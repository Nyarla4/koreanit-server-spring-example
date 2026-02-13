package com.koreanit.spring.comment;

import java.util.List;

public interface CommentRepository {
    long save(long userId, long postId, String content);
    CommentEntity findById(long id);
    List<CommentEntity> findAll(long postId, int limit);
    int delete(long id);
    boolean isOwner(long commentId, long userId);
}