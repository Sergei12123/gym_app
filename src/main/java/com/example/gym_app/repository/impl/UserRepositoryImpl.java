package com.example.gym_app.repository.impl;

import com.example.gym_app.entity.User;
import com.example.gym_app.repository.BaseRepository;
import com.example.gym_app.repository.UserRepository;
import com.example.gym_app.utils.StringCaseUtil;

public abstract class UserRepositoryImpl<T extends User> extends BaseRepositoryImpl<T> implements UserRepository<T>, BaseRepository<T> {

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName()) + " WHERE user_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

}
