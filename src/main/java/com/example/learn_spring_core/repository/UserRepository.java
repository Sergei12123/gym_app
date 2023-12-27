package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.User;

public interface UserRepository<T extends User> extends BaseRepository<T> {

    boolean existsByUsername(String username);

}
