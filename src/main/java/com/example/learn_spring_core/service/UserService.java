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
     * @return updated user
     */
    T update(T user);

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
     * Change the password for a user.
     *
     * @param userName    the username of the user
     * @param oldPassword the user's current password
     * @param newPassport the new password to set for the user
     */
    void changePassword(String userName, String oldPassword, String newPassport);

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
     * Sets the active status of a user.
     *
     * @param userName the username of the user
     * @param isActive the active status to set
     */
    void setActive(String userName, boolean isActive);

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
     * @param lastName  lastName of user
     * @return true if user exist
     */
    boolean existByFirstNameAndLastNameActiveUser(String firstName, String lastName);

}
