package com.example.gym_app.service;

import com.example.gym_app.entity.User;

public interface UserService<T extends User> extends BaseService<T> {

    T create(T user);

    void update(T user);

    String generateUsername(String firstName, String lastName);

    String generateRandomPassword();

    void changePassword(Long userId, String newPassword);

    void activate(Long userId);

    void deactivate(Long userId);


}
