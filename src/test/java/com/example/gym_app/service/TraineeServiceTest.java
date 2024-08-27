package com.example.gym_app.service;

import com.example.gym_app.TestsParent;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.example.gym_app.utils.SampleCreator.createSampleTrainee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TraineeServiceTest extends TestsParent {

    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        when(traineeRepository.getEntityClass()).thenReturn(Trainee.class); // Set the entity class to Trainer
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee traineeArgument = invocation.getArgument(0);
            traineeArgument.setId(1L);
            return traineeArgument;
        });
    }

    @Test
    void testCreateTrainee() {

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        trainee.setAddress("123 Main St");

        when(traineeRepository.existsByUsername(Mockito.anyString())).thenReturn(false);

        traineeService.create(trainee);
        Mockito.verify(traineeRepository, times(1)).save(Mockito.any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        Long traineeIdToDelete = 1L;
        doNothing().when(traineeRepository).deleteById(traineeIdToDelete);

        traineeService.delete(traineeIdToDelete);

        Mockito.verify(traineeRepository, times(1)).deleteById(traineeIdToDelete);
    }

    @Test
    void testFindAllTrainees() {
        Trainee trainee1 = createSampleTrainee(true);

        Trainee trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setFirstName("Alice");
        trainee2.setLastName("Smith");
        trainee2.setDateOfBirth(LocalDate.of(1985, 8, 22));
        trainee2.setAddress("456 Oak St");
        trainee2.setUserName("alice.smith");
        trainee2.setPassword("securepass");
        trainee2.setIsActive(true);

        List<Trainee> trainees = Arrays.asList(trainee1, trainee2);
        when(traineeRepository.findAll()).thenReturn(trainees);

        List<Trainee> allTrainees = traineeService.findAll();

        assertEquals(trainees, allTrainees);
        verify(traineeRepository, times(1)).findAll();
    }

    @Test
    void testGetTraineeById() {
        Long traineeId = 1L;
        Trainee trainee = createSampleTrainee(true);

        when(traineeRepository.getById(traineeId)).thenReturn(trainee);

        Trainee retrievedTrainee = traineeService.getById(traineeId);

        assertEquals(trainee, retrievedTrainee);
        verify(traineeRepository, times(1)).getById(traineeId);
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.getById(traineeId)).thenReturn(sampleTrainee);

        traineeService.update(sampleTrainee);

        verify(traineeRepository, times(1)).update(any(Trainee.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(traineeRepository.existsByUsername(expectedUserName)).thenReturn(false);
        String result = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(traineeRepository, times(1)).existsByUsername(anyString());


        when(traineeRepository.existsByUsername(expectedUserName)).thenReturn(true);
        when(traineeRepository.existsByUsername(expectedUserName1)).thenReturn(false);

        String result2 = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(traineeRepository, times(3)).existsByUsername(anyString());

    }

    @Test
    void testGenerateRandomPassword() {
        for (int i = 0; i < 5; i++) {
            String result = traineeService.generateRandomPassword();
            Assertions.assertEquals(10, result.length(), "Password length should be 10 characters");
            for (char ch : result.toCharArray()) {
                Assertions.assertTrue(Character.isLetterOrDigit(ch));
            }
        }
    }

}
