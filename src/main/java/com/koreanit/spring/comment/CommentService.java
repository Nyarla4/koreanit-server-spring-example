package com.koreanit.spring.comment;

import com.koreanit.spring.common.error.ApiException;
import com.koreanit.spring.common.error.ErrorCode;
import com.koreanit.spring.post.PostRepository;
import com.koreanit.spring.security.SecurityUtils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

  private static final int MAX_LIMIT = 1000;

  private final CommentRepository commentRepository;

  private final PostRepository postRepository;

  public CommentService(CommentRepository commentRepository,
      PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  private int normalizeLimit(int limit) {
    if (limit <= 0) {
      throw new ApiException(ErrorCode.INVALID_REQUEST, "limit 값이 유효하지 않습니다");
    }
    return Math.min(limit, MAX_LIMIT);
  }

  // private int normalizePage(int page) {
  //   if (page <= 0) {
  //     throw new ApiException(ErrorCode.INVALID_REQUEST, "page 값이 유효하지 않습니다");
  //   }
  //   return page;
  // }

  @Transactional
  public Comment create(long userId, long postId, String content) {
    try {
      int updated = postRepository.increaseCommentsCnt(postId);
      if (updated == 0) {
        throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE,
            "존재하지 않는 게시글입니다. id=" + postId);
      }
      long id = commentRepository.save(userId, postId, content);
      return CommentMapper.toDomain(commentRepository.findById(id));
    } catch (DataIntegrityViolationException e) {
      throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE,
          "댓글 작성에 실패하였습니다.");
    }
  }

  public List<Comment> list(long postId, int limit) {
    try {
      postRepository.findById(postId);
    } catch (EmptyResultDataAccessException e) {
      throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE,
          "존재하지 않는 게시글입니다. id=" + postId);
    }
    limit = normalizeLimit(limit);
    return CommentMapper.toDomainList(commentRepository.findAll(postId, limit));
  }

  @PreAuthorize("hasRole('ADMIN') or @commentService.isOwner(#postId, #id)")
  public void delete(long id) {
    // int updated = postRepository.decreaseCommentsCnt(postId);
    // if (updated == 0) {
    //   throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE,
    //       "존재하지 않는 게시글입니다. id=" + postId);
    // }

    int deleted = commentRepository.delete(id);
    if (deleted == 0) {
      throw new ApiException(
          ErrorCode.NOT_FOUND_RESOURCE,
          "존재하지 않는 댓글입니다. id=" + id);
    }
  }

  /**
   * 현재 로그인 사용자가 해당 게시글의 작성자인지 확인
   * - PreAuthorize 전용 보조 메서드
   */
  public boolean isOwner(Long postId, Long commentId) {
    Long currentUserId = SecurityUtils.currentUserId();
    if (currentUserId == null) {
      return false;
    }

    return commentRepository.isOwner(commentId, currentUserId);
  }
}