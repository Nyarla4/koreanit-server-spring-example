package com.koreanit.spring.comment;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<CommentEntity> rowMapper = (rs, rowNum) -> {
    CommentEntity e = new CommentEntity();
    e.setId(rs.getLong("id"));
    e.setPostId(rs.getLong("post_Id"));
    e.setUserId(rs.getLong("user_id"));
    e.setContent(rs.getString("content"));
    e.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    return e;
  };

  @Override
  public long save(long userId, long postId, String content) {
    String sql = "INSERT INTO comments(user_id, post_id, content, created_at) VALUES (?, ?, ?, NOW())";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(con -> {
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setLong(1, userId);
      ps.setLong(2, postId);
      ps.setString(3, content);
      return ps;
    }, keyHolder);

    return keyHolder.getKey().longValue();
  }

  @Override
  public List<CommentEntity> findAll(long postId, int limit) {
    String sql = """
        SELECT id, post_id, user_id, content, created_at
        FROM comments
        WHERE post_id = ?
        ORDER BY id DESC
        LIMIT ?
        """;
    return jdbcTemplate.query(sql, rowMapper, postId, limit);
  }

@Override
  public CommentEntity findById(long id) {
    String sql = """
        SELECT id, post_id, user_id, content, created_at
        FROM comments
        WHERE id = ?
        """;
    return jdbcTemplate.queryForObject(sql, rowMapper, id);
  }
  
  @Override
  public int delete(long id) {
    String sql = "DELETE FROM comments WHERE id = ?";
    return jdbcTemplate.update(sql, id);
  }

  @Override
  public boolean isOwner(long commentId, long userId) {
      String sql = """
          SELECT COUNT(*)
          FROM comments
          WHERE id = ? AND user_id = ?
      """;

      int count = jdbcTemplate.queryForObject(
          sql,
          Integer.class,
          commentId,
          userId
      );

      return count > 0;
  }

}