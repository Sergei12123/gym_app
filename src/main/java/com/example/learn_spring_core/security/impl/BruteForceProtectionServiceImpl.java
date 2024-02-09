package com.example.learn_spring_core.security.impl;

import com.example.learn_spring_core.security.BruteForceProtectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService {

    @Value("${security.brute.max.try}")
    private int maxFailedLogins;

    @Value("${security.brute.timeout.minutes}")
    private int timeout;

    private final Map<String, Integer> loginAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> lockoutTime = new HashMap<>();

    @Override
    public void registerLoginFailure(String username) {
        loginAttempts.put(username, loginAttempts.get(username) == null ? 1 : loginAttempts.get(username) + 1);
        if (loginAttempts.get(username) >= maxFailedLogins) {
            lockoutTime.put(username, LocalDateTime.now().plusMinutes(timeout));
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        loginAttempts.remove(username);
        lockoutTime.remove(username);
    }

    @Override
    public boolean isAllowedToLogin(String userName) {
        return loginAttempts.get(userName) == null || loginAttempts.get(userName) < maxFailedLogins
            || lockoutTime.get(userName) == null || lockoutTime.get(userName).isBefore(LocalDateTime.now());
    }

    @Override
    public String getTillForUsername(String username) {
        return lockoutTime.get(username).toString();
    }

}
