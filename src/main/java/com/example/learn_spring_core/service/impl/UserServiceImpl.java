package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.User;
import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.service.UserService;
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
        Long suffix = ((UserRepository<?>) currentRepository).countByFirstNameAndLastName(firstName, lastName);
        String username = baseUsername + (suffix > 0 ? String.valueOf(suffix) : "");
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
    public void deleteByUserName(String userName) {
        ((UserRepository<T>) currentRepository).removeByUserName(userName);
    }

    @Override
    public T findByUserName(String userName) {
        return ((UserRepository<T>) currentRepository).findByUserName(userName);
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

