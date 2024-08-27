package com.example.gym_app.security;

public interface BruteForceProtectionService {

    /**
     * Registers a login failure for the specified username.
     *
     * @param username the username for which the login failure is being registered
     */
    void registerLoginFailure(String username);

    /**
     * Resets the brute force counter for the specified username.
     *
     * @param username the username for which the brute force counter is being reset
     */
    void resetBruteForceCounter(String username);

    /**
     * Checks if the specified username is allowed to login.
     *
     * @param userName the username to be checked
     * @return true if the username is allowed to login, false otherwise
     */
    boolean isAllowedToLogin(String userName);

    /**
     * Returns the time till the next login attempt for the specified username.
     *
     * @param username the username for which the time till the next login attempt is being returned
     * @return the time till the next login attempt
     */
    String getTillForUsername(String username);
}
