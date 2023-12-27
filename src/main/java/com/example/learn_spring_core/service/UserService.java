package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.User;

public interface UserService<T extends User> extends BaseService<T>{

    T create(T user);

    void update(T user);

    String generateUsername(String firstName, String lastName);

    String generateRandomPassword();

}
