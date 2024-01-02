package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainer;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerServiceTest extends TestsParent {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        when(trainerRepository.getEntityClass()).thenReturn(Trainer.class); // Set the entity class to Trainer
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
        when(trainerRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

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

        when(trainerRepository.getById(traineeId)).thenReturn(trainer);

        Trainer retrievedTrainee = trainerService.getById(traineeId);

        assertEquals(trainer, retrievedTrainee);
        verify(trainerRepository, times(1)).getById(traineeId);
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainer sampleTrainee = createSampleTrainer(true);
        when(trainerRepository.getById(traineeId)).thenReturn(sampleTrainee);

        trainerService.update(sampleTrainee);

        verify(trainerRepository, times(1)).update(any(Trainer.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(trainerRepository.existsByUsername(expectedUserName)).thenReturn(false);
        String result = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(trainerRepository, times(1)).existsByUsername(anyString());


        when(trainerRepository.existsByUsername(expectedUserName)).thenReturn(true);
        when(trainerRepository.existsByUsername(expectedUserName1)).thenReturn(false);

        String result2 = trainerService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(trainerRepository, times(3)).existsByUsername(anyString());

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

}
