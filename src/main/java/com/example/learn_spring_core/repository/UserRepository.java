package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.utils.StringCaseUtil;

public abstract class UserRepository<T> extends BaseRepository<T> {

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName()) + " WHERE user_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

}
