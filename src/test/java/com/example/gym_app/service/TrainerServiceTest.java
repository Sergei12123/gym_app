package com.example.gym_app.service;

import com.example.gym_app.TestsParent;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.service.impl.TraineeServiceImpl;
import com.example.gym_app.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.example.gym_app.utils.SampleCreator.createSampleTrainer;
import static com.example.gym_app.utils.SampleCreator.createSampleTrainers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerServiceTest extends TestsParent {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

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
        Long traineeId = 1L;
        Trainer sampleTrainee = createSampleTrainer(true);
        when(trainerRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));

        trainerService.update(sampleTrainee);

        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(trainerRepository.existsByUserName(expectedUserName)).thenReturn(false);
        String result = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(trainerRepository, times(1)).existsByUserName(anyString());


        when(trainerRepository.existsByUserName(expectedUserName)).thenReturn(true);
        when(trainerRepository.existsByUserName(expectedUserName1)).thenReturn(false);

        String result2 = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(trainerRepository, times(3)).existsByUserName(anyString());

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
        Long trainerId = 1L;
        Trainer sampleTrainer = createSampleTrainer(true);
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(sampleTrainer));
        trainerService.changePassword(trainerId, "newPassword");
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

}
