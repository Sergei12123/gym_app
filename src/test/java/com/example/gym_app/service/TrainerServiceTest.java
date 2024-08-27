package com.example.gym_app.service;

import com.example.gym_app.TestsParent;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.exception.IncorrectCredentialsException;
import com.example.gym_app.exception.UserNotAllowedToLoginException;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.repository.UserRepository;
import com.example.gym_app.security.BruteForceProtectionService;
import com.example.gym_app.security.JwtService;
import com.example.gym_app.service.impl.TraineeServiceImpl;
import com.example.gym_app.service.impl.TrainerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.gym_app.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TrainerServiceTest extends TestsParent {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BruteForceProtectionService bruteForceProtectionService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field hack = TrainerServiceImpl.class.getSuperclass().getSuperclass().getDeclaredField("currentRepository");
        hack.setAccessible(true);
        hack.set(trainerService, trainerRepository);
        Field hack2 = TrainerServiceImpl.class.getSuperclass().getDeclaredField("passwordEncoder");
        hack2.setAccessible(true);
        hack2.set(trainerService, passwordEncoder);
        Field hack3 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("bruteForceProtectionService");
        hack3.setAccessible(true);
        hack3.set(trainerService, bruteForceProtectionService);
        Field hack4 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("authenticationManager");
        hack4.setAccessible(true);
        hack4.set(trainerService, authenticationManager);
        Field hack5 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("jwtService");
        hack5.setAccessible(true);
        hack5.set(trainerService, jwtService);
        when(trainerRepository.save(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainerArgument = invocation.getArgument(0);
            trainerArgument.setId(1L);
            return trainerArgument;
        });
    }

    @Test
    void testLogin() {
        String userName = "sampleUser";
        String password = "samplePassword";
        Authentication authentication = new UsernamePasswordAuthenticationToken(createSampleTrainee(true), password);
        String expectedResult = "generatedToken";
        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(true);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(expectedResult);
        String result = trainerService.login(userName, password);
        Assertions.assertEquals(expectedResult, result);

        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(true);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);
        assertThrows(IncorrectCredentialsException.class, () -> trainerService.login(userName, password));

        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(false);
        when(bruteForceProtectionService.getTillForUsername(userName)).thenReturn(LocalDateTime.now().plusMinutes(15).toString());
        assertThrows(UserNotAllowedToLoginException.class, () -> trainerService.login(userName, password));
    }

    @Test
    void testCreateTrainee() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setTrainingType(new TrainingType());
        when(trainerRepository.existsByUserName(Mockito.anyString())).thenReturn(false);
        when(trainingTypeService.findByName(trainer.getTrainingType().getTrainingTypeName())).thenReturn(new TrainingType());
        trainerService.create(trainer);

        Mockito.verify(trainerRepository, times(1)).save(Mockito.any(Trainer.class));
    }

    @Test
    void testFindAllTrainees() {
        List<Trainer> trainees = createSampleTrainers(true, 3);
        when(trainerRepository.findAll()).thenReturn(trainees);

        List<Trainer> allTrainees = trainerService.findAll();

        assertEquals(trainees, allTrainees);
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    void testGetTraineeById() {
        Long traineeId = 1L;
        Trainer trainer = createSampleTrainer(true);

        when(trainerRepository.findById(traineeId)).thenReturn(Optional.of(trainer));

        Trainer retrievedTrainee = trainerService.getById(traineeId);

        assertEquals(trainer, retrievedTrainee);
        verify(trainerRepository, times(1)).findById(traineeId);
    }

    @Test
    void testUpdateTrainee() {
        Trainer sampleTrainee = createSampleTrainer(true);
        when(trainerRepository.existsByUserName(sampleTrainee.getUserName())).thenReturn(true);

        trainerService.update(sampleTrainee);

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(trainerRepository.countByUserNameStartsWith(expectedUserName)).thenReturn(0L);
        String result = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(trainerRepository, times(1)).countByUserNameStartsWith(expectedUserName);


        when(trainerRepository.countByUserNameStartsWith(expectedUserName)).thenReturn(1L);
        String result2 = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(((UserRepository<?>) trainerRepository), times(2)).countByUserNameStartsWith(expectedUserName);

    }

    @Test
    void testGenerateRandomPassword() {
        for (int i = 0; i < 5; i++) {
            String result = trainerService.generateRandomPassword();
            Assertions.assertEquals(10, result.length(), "Password length should be 10 characters");
            for (char ch : result.toCharArray()) {
                Assertions.assertTrue(Character.isLetterOrDigit(ch));
            }
        }
    }

    @Test
    void testChangePassword() {
        Trainer sampleTrainer = createSampleTrainer(true);
        when(trainerRepository.findByUserName(sampleTrainer.getUserName())).thenReturn(Optional.of(sampleTrainer));
        when(passwordEncoder.matches(sampleTrainer.getPassword(), sampleTrainer.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        trainerService.changePassword(sampleTrainer.getUserName(), sampleTrainer.getPassword(), "newPassword");
        Assertions.assertEquals("newEncodedPassword", sampleTrainer.getPassword());

        Trainer sampleTrainerNotFound = createSampleTrainer(true);
        when(trainerRepository.findByUserName(sampleTrainer.getUserName())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> trainerService.changePassword(sampleTrainerNotFound.getUserName(), sampleTrainerNotFound.getPassword(), "newPassword"));

        Trainer sampleTrainerIncorrectPass = createSampleTrainer(true);
        when(trainerRepository.findByUserName(sampleTrainerIncorrectPass.getUserName())).thenReturn(Optional.of(sampleTrainerIncorrectPass));
        when(passwordEncoder.matches(sampleTrainerIncorrectPass.getPassword(), sampleTrainerIncorrectPass.getPassword())).thenReturn(false);

        assertThrows(IncorrectCredentialsException.class, () -> trainerService.changePassword(sampleTrainerIncorrectPass.getUserName(), sampleTrainerIncorrectPass.getPassword(), "newPassword"));

    }

    @Test
    void testActivate() {
        Long trainerId = 1L;
        Trainer sampleTrainer = createSampleTrainer(true);
        sampleTrainer.setIsActive(false);
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(sampleTrainer));
        trainerService.activate(trainerId);
        Assertions.assertTrue(sampleTrainer.getIsActive());
    }

    @Test
    void testDeactivate() {
        Long trainerId = 1L;
        Trainer sampleTrainer = createSampleTrainer(true);
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(sampleTrainer));
        trainerService.deactivate(trainerId);
        Assertions.assertFalse(sampleTrainer.getIsActive());
    }

    @Test
    void testAddTraineeToTrainer() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setId(2L);

        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(trainerRepository.save(trainer)).thenAnswer(invocationOnMock -> {
            Trainer trainer1 = invocationOnMock.getArgument(0);
            trainer1.getTrainees().add(trainee);
            return trainer1;
        });
        when(trainerRepository.findById(2L)).thenReturn(Optional.of(trainer));

        trainerService.addTraineeToTrainer(1L, 2L);

        Assertions.assertTrue(trainer.getTrainees().contains(trainee));
    }

    @Test
    void testRemoveTrainerFromTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setId(2L);

        trainer.getTrainees().add(trainee);

        when(trainerRepository.findById(2L)).thenReturn(Optional.of(trainer));

        trainerService.removeTraineeFromTrainer(2L, 1L);

        Assertions.assertTrue(trainer.getTrainees().isEmpty());
    }

    @Test
    void testGetNotAssignedActiveTrainers() {
        List<Trainer> notAssignedTrainers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            notAssignedTrainers.add(createSampleTrainer(false));
        }

        when(trainerRepository.findByTraineesNullAndIsActiveTrue()).thenReturn(notAssignedTrainers);

        List<Trainer> result = trainerService.getNotAssignedActiveTrainers();

        Assertions.assertEquals(notAssignedTrainers, result);
    }

    @Test
    void testGetNotAssignedToConcreteTraineeActiveTrainers() {
        String traineeUserName = "sampleTrainee";
        Trainee trainee = createSampleTrainee(true);
        trainee.setUserName(traineeUserName);


        List<Trainer> notAssignedTrainers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            notAssignedTrainers.add(createSampleTrainer(false));
        }

        when(traineeRepository.existsByUserName(traineeUserName)).thenReturn(true);
        when(trainerRepository.findByTrainees_UserNameNotContainingAndIsActiveTrue(traineeUserName)).thenReturn(notAssignedTrainers);

        List<Trainer> result = trainerService.getNotAssignedToConcreteTraineeActiveTrainers(traineeUserName);

        Assertions.assertEquals(notAssignedTrainers, result);
    }

    @Test
    void testExistByFirstNameAndLastNameActiveUser() {
        String firstName = "John";
        String lastName = "Doe";
        boolean expectedResult = true;

        when(trainerRepository.existsByFirstNameAndLastNameAndIsActiveTrue(firstName, lastName)).thenReturn(expectedResult);

        boolean result = trainerService.existByFirstNameAndLastNameActiveUser(firstName, lastName);

        Assertions.assertEquals(expectedResult, result);
    }

}
