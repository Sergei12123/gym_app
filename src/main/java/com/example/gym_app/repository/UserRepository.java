package com.example.gym_app.repository;

import com.example.gym_app.entity.User;

public interface UserRepository<T extends User> extends BaseRepository<T> {

    boolean existsByUsername(String username);

}
