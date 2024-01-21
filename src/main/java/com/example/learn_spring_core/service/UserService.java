package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.User;

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
     * @param lastName last name of user
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
     * @param userId id of user
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

    /**
     * Delete user by username
     *
     * @param userName username
     */
    void deleteByUserName(String userName);

    /**
     * Get user by username
     *
     * @param userName username
     * @return user
     */
    T findByUserName(String userName);

    /**
     * Check userName and password on existing user.
     *
     * @param userName username
     * @param password password
     * @return true if user exists
     */
    boolean login(String userName, String password);

    /**
     * Check if user exist with supplied firstName and lastName
     *
     * @param firstName firstName of user
     * @param lastName lastName of user
     * @return true if user exist
     */
    boolean existByFirstNameAndLastNameActiveUser(String firstName, String lastName);

}
