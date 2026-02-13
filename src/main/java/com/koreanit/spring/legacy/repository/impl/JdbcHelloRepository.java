package com.koreanit.spring.legacy.repository.impl;

import com.koreanit.spring.legacy.repository.HelloRepository;
import com.koreanit.spring.post.PostEntity;
import com.koreanit.spring.user.UserEntity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;

@Repository//자동을 new JdbcHelloRepository클래스를 생성해서 bean에 등록
public class JdbcHelloRepository implements HelloRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcHelloRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * result를 userEntity로 변환
     * */
    private final RowMapper<UserEntity> userRowMapper = (rs, rowNum) -> {
        UserEntity u = new UserEntity();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setNickname(rs.getString("nickname"));

        Timestamp c = rs.getTimestamp("created_at");
        if (c != null) {
            u.setCreatedAt(c.toLocalDateTime());
        }

        Timestamp up = rs.getTimestamp("updated_at");
        if (up != null) {
            u.setUpdatedAt(up.toLocalDateTime());
        }

        return u;
    };

    private final RowMapper<PostEntity> postRowMapper = (rs, rowNum) -> {
        PostEntity p = new PostEntity();
        p.setId(rs.getLong("id"));
        p.setUserId(rs.getLong("user_id"));
        p.setTitle(rs.getString("title"));
        p.setContent(rs.getString("content"));
        p.setViewCount(rs.getInt("view_count"));
        p.setCommentsCnt(rs.getInt("comments_cnt"));

        Timestamp c = rs.getTimestamp("created_at");
        if (c != null) {
            p.setCreatedAt(c.toLocalDateTime());
        }

        Timestamp up = rs.getTimestamp("updated_at");
        if (up != null) {
            p.setUpdatedAt(up.toLocalDateTime());
        }

        return p;
    };

    @Override
    public List<UserEntity> findUsers(int limit) {
        String sql =
            "SELECT id, username, email, password, nickname, created_at, updated_at " +
            //"FROM users ORDER BY id DESC LIMIT 1000";   //기존(상수)
            "FROM users ORDER BY id DESC LIMIT ?";  //수정(변수)
        //? 갯수만큼 뒤에 추가 param 넣을 것
        return jdbcTemplate.query(sql, userRowMapper, limit);
    }

    @Override
    public UserEntity findUserById(Long id) {
        String sql =
            "SELECT id, username, email, password, nickname, created_at, updated_at " +
            "FROM users WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, userRowMapper, id);
    }

    @Override
    public List<PostEntity> findPosts(int limit) {
        String sql =
            "SELECT id, user_id, title, content, view_count, comments_cnt, created_at, updated_at " +
            "FROM posts ORDER BY id DESC LIMIT ?";

        return jdbcTemplate.query(sql, postRowMapper, limit);
    }

    @Override
    public PostEntity findPostById(Long id) {
        String sql =
            "SELECT id, user_id, title, content, view_count, comments_cnt, created_at, updated_at " +
            "FROM posts WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, postRowMapper, id);
    }
}