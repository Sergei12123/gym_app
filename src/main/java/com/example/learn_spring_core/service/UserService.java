package com.example.learn_spring_core.service;

import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.repository.entity.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public abstract class UserService<T extends User> extends BaseService<T> {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    protected String generateUsername(String firstName, String lastName) {
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

    protected String generateRandomPassword() {
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

