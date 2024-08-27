package com.example.gym_app.service.impl;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.User;
import com.example.gym_app.repository.UserRepository;
import com.example.gym_app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;

@Service
@Transactional
public abstract class UserServiceImpl<T extends User> extends BaseServiceImpl<T> implements UserService<T> {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        while (((UserRepository<?>) currentRepository).existsByUserName(username)) {
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
    public void changePassword(Long userId, String newPassword) {
        T user = getById(userId);
        user.setPassword(newPassword);
    }

    @Override
    public void activate(Long userId) {
        T user = getById(userId);
        if (Boolean.FALSE.equals(user.getIsActive())) user.setIsActive(true);
    }

    @Override
    public void deactivate(Long userId) {
        T user = getById(userId);
        if (Boolean.TRUE.equals(user.getIsActive())) user.setIsActive(false);
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

    @Override
    public void update(T user) {
        logger.info("Update entity {}. Set:", getCurrentEntityName());
        logEntityObject(user);
        currentRepository.save(user);
    }

    protected void createSampleTraining(Trainer trainer, Trainee trainee) {
        Training training = new Training();
        training.setTrainingName("new training");
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingDate(LocalDate.now().plusDays(1));
        training.setTrainingDuration(1L);
        trainee.getTrainings().add(training);
        trainer.getTrainings().add(training);

    }

}

