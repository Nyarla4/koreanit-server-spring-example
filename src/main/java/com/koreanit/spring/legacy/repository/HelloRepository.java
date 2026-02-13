package com.koreanit.spring.legacy.repository;

import com.koreanit.spring.post.PostEntity;
import com.koreanit.spring.user.UserEntity;

import java.util.List;

public interface HelloRepository {

    List<UserEntity> findUsers(int limit);

    UserEntity findUserById(Long id);

    List<PostEntity> findPosts(int limit);

    PostEntity findPostById(Long id);
}