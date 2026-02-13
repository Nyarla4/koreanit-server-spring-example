package com.koreanit.spring.comment;

import java.util.ArrayList;
import java.util.List;

import com.koreanit.spring.comment.dto.response.CommentResponse;

public class CommentMapper {

    private CommentMapper() {}

    // Entity -> Domain (단건)
    public static Comment toDomain(CommentEntity e) {
        return Comment.of(
                e.getId(),
                e.getPostId(),
                e.getUserId(),
                e.getContent(),
                e.getCreatedAt()
        );
    }

    // Entity -> Domain (리스트)
    public static List<Comment> toDomainList(List<CommentEntity> entities) {
        List<Comment> result = new ArrayList<>(entities.size());
        for (CommentEntity e : entities) {
            result.add(toDomain(e));
        }
        return result;
    }

    // Domain -> Response DTO (단건)
    public static CommentResponse toResponse(Comment c) {
        return CommentResponse.from(c);
    }

    // Domain -> Response DTO (리스트)
    public static List<CommentResponse> toResponseList(List<Comment> comments) {
        List<CommentResponse> result = new ArrayList<>(comments.size());
        for (Comment c : comments) {
            result.add(toResponse(c));
        }
        return result;
    }
}
