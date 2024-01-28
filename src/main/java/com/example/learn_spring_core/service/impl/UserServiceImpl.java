package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.component.TransactionIdHolder;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.User;
import com.example.learn_spring_core.exception.IncorrectCredentialsException;
import com.example.learn_spring_core.exception.UserAlreadyExistsException;
import com.example.learn_spring_core.exception.UserNotAllowedToLoginException;
import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.security.BruteForceProtectionService;
import com.example.learn_spring_core.security.JwtService;
import com.example.learn_spring_core.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public abstract class UserServiceImpl<T extends User> extends BaseServiceImpl<T> implements UserService<T> {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static final String USER_NOT_FOUND_EX = "User with username %s not found";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BruteForceProtectionService bruteForceProtectionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @Override
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        Long suffix = ((UserRepository<?>) currentRepository).countByUserNameStartsWith(baseUsername);
        String username = baseUsername + (suffix > 0 ? String.valueOf(suffix) : "");
        log.info("Transaction: {}. For the {} {} {}, has been generated the nickname {}", TransactionIdHolder.getTransactionId(), getCurrentEntityName(), firstName, lastName, username);
        return username;
    }

    @Override
    public String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        log.info("Transaction: {}. Password was generated for the {}", TransactionIdHolder.getTransactionId(), getCurrentEntityName());
        return password.toString();
    }

    @Override
    public void changePassword(String userName, String oldPassword, String newPassword) {
        Optional<T> user = ((UserRepository<T>) currentRepository).findByUserName(userName);
        if (user.isPresent()) {
            if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(newPassword));
            } else {
                throw new IncorrectCredentialsException();
            }
        } else {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(userName));
        }
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
    public void setActive(String userName, boolean isActive) {
        Optional<T> user = ((UserRepository<T>) currentRepository).findByUserName(userName);
        if (user.isPresent()) {
            user.get().setIsActive(isActive);
        } else {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(userName));
        }
    }

    @Override
    public void deleteByUserName(String userName) {
        Optional<T> user = ((UserRepository<T>) currentRepository).findByUserName(userName);
        if (user.isPresent()) {
            currentRepository.delete(user.get());
        } else {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(userName));
        }

    }

    @Override
    public T findByUserName(String userName) {
        return ((UserRepository<T>) currentRepository).findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(userName)));
    }

    @Override
    public UserCredentialsDTO create(T user) {
        if (((UserRepository<T>) currentRepository).existsByFirstNameAndLastNameAndIsActiveTrue(user.getFirstName(), user.getLastName())) {
            throw new UserAlreadyExistsException();
        }

        user.setUserName(generateUsername(user.getFirstName(), user.getLastName()));
        String password = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        currentRepository.save(user);
        return UserCredentialsDTO.builder()
                .username(user.getUsername())
                .password(password)
                .build();
    }

    @Override
    public T update(T user) {
        if (!((UserRepository<T>) currentRepository).existsByUserName(user.getUsername())) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(user.getUsername()));
        }
        return currentRepository.save(user);
    }

    @Override
    public String login(String userName, String password) {
        try {
            if (bruteForceProtectionService.isAllowedToLogin(userName)) {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userName, password));
                bruteForceProtectionService.resetBruteForceCounter(userName);
                return jwtService.generateToken((UserDetails) authenticate.getPrincipal());
            } else {
                throw new UserNotAllowedToLoginException("User" + userName + " cant login till " + bruteForceProtectionService.getTillForUsername(userName));

            }
        } catch (BadCredentialsException exception) {
            bruteForceProtectionService.registerLoginFailure(userName);
            throw new IncorrectCredentialsException(userName);
        }
    }

    @Override
    public boolean existByFirstNameAndLastNameActiveUser(String firstName, String lastName) {
        return ((UserRepository<?>) currentRepository).existsByFirstNameAndLastNameAndIsActiveTrue(firstName, lastName);
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

