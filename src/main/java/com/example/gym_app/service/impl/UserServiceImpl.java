package com.example.gym_app.service.impl;

import com.example.gym_app.entity.User;
import com.example.gym_app.repository.UserRepository;
import com.example.gym_app.service.UserService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public abstract class UserServiceImpl<T extends User> extends BaseServiceImpl<T> implements UserService<T> {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        while (((UserRepository<?>) currentRepository).existsByUsername(username)) {
            username = baseUsername + suffix;
            suffix++;
        }
        logger.info("For the {} {} {}, has been generated the nickname {}", getCurrentEntityName(), firstName, lastName, username);
        return username;
    }

    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        logger.info("Password was generated for the {}", getCurrentEntityName());
        return password.toString();
    }

    @Override
    public T create(T user) {
        user.setUserName(generateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(generateRandomPassword());
        user.setIsActive(true);
        logger.info("Create entity {}", getCurrentEntityName());
        logEntityObject(user);
        currentRepository.save(user);
        logger.info("Entity {} successfully saved with id = {}", getCurrentEntityName(), user.getId());
        return user;
    }

    public void update(T user) {
        logger.info("Update entity {}. Set:", getCurrentEntityName());
        logEntityObject(user);
        currentRepository.update(user);
    }

}

