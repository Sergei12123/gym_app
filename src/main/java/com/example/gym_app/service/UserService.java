package com.example.gym_app.service;

import com.example.gym_app.entity.User;

public interface UserService<T extends User> extends BaseService<T> {

    /**
     * Create new user with all fields
     *
     * @param user user without userName and password
     * @return new user with id
     */
    T create(T user);

    /**
     * Update existing user
     *
     * @param user user with new data
     */
    void update(T user);

    /**
     * Generate username
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @return username for user
     */
    String generateUsername(String firstName, String lastName);

    /**
     * Generate random password for user
     *
     * @return generated password
     */
    String generateRandomPassword();

    /**
     * Change user password
     *
     * @param userId      id of user
     * @param newPassword new password
     */
    void changePassword(Long userId, String newPassword);

    /**
     * Set user active to true
     *
     * @param userId id of user
     */
    void activate(Long userId);

    /**
     * Set user active to false
     *
     * @param userId id of user
     */
    void deactivate(Long userId);

}
