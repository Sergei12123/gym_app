package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.User;
import com.example.learn_spring_core.repository.BaseRepository;
import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.utils.StringCaseUtil;

public abstract class UserRepositoryImpl<T extends User> extends BaseRepositoryImpl<T> implements UserRepository<T>, BaseRepository<T> {

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName()) + " WHERE user_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

}
