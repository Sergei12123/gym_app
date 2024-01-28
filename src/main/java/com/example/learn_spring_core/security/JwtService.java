package com.example.learn_spring_core.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    /**
     * Extracts the username from the given token.
     *
     * @param  token  the token to be extracted
     * @return        the username
     */
    String extractUserName(String token);

    /**
     * Generates a token for the given user details.
     *
     * @param  userDetails  the user details for which the token is generated
     * @return              the generated token
     */
    String generateToken(UserDetails userDetails);

    /**
     * Checks if the given token is valid for the given user details.
     *
     * @param  token        the token to be checked
     * @param  userDetails  the user details for which the token is valid
     * @return              true if the token is valid, false otherwise
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
