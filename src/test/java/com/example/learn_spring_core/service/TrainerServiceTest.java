package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.service.impl.TraineeServiceImpl;
import com.example.learn_spring_core.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerServiceTest extends TestsParent {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field hack = TraineeServiceImpl.class.getSuperclass().getSuperclass().getDeclaredField("currentRepository");
        hack.setAccessible(true);
        hack.set(trainerService, trainerRepository);

        when(trainerRepository.save(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainerArgument = invocation.getArgument(0);
            trainerArgument.setId(1L);
            return trainerArgument;
        });
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
        when(trainerRepository.existsByUserNameAndPassword(sampleTrainer.getUserName(), sampleTrainer.getPassword())).thenReturn(true);

        trainerService.changePassword(sampleTrainer.getUserName(), sampleTrainer.getPassword(), "newPassword");
        Assertions.assertEquals("newPassword", sampleTrainer.getPassword());
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
    void testLogin() {
        String userName = "sampleUser";
        String password = "samplePassword";
        boolean expectedResult = true;

        when(trainerRepository.existsByUserNameAndPassword(userName, password)).thenReturn(expectedResult);

        boolean result = trainerService.login(userName, password);

        Assertions.assertEquals(expectedResult, result);
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
