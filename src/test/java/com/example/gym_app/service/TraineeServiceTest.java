package com.example.gym_app.service;

import com.example.gym_app.TestsParent;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.repository.UserRepository;
import com.example.gym_app.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.gym_app.utils.SampleCreator.createSampleTrainee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TraineeServiceTest extends TestsParent {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field hack = TraineeServiceImpl.class.getSuperclass().getSuperclass().getDeclaredField("currentRepository");
        hack.setAccessible(true);
        hack.set(traineeService, traineeRepository);
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee traineeArgument = invocation.getArgument(0);
            traineeArgument.setId(1L);
            return traineeArgument;
        });
        when(trainerRepository.save(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainerArgument = invocation.getArgument(0);
            trainerArgument.setId(1L);
            return trainerArgument;
        });

    }

    @Test
    void testCreateTrainee() {

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        trainee.setAddress("123 Main St");

        when(traineeRepository.existsByUserName(Mockito.anyString())).thenReturn(false);

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
        trainee2.setPassword("secure-pass");
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

        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));

        Trainee retrievedTrainee = traineeService.getById(traineeId);

        assertEquals(trainee, retrievedTrainee);
        verify(traineeRepository, times(1)).findById(traineeId);
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));

        traineeService.update(sampleTrainee);

        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(traineeRepository.countByFirstNameAndLastName(firstName, lastName)).thenReturn(0L);
        String result = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(traineeRepository, times(1)).countByFirstNameAndLastName(firstName, lastName);


        when(traineeRepository.countByFirstNameAndLastName(firstName, lastName)).thenReturn(1L);
        String result2 = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(((UserRepository<?>) traineeRepository), times(2)).countByFirstNameAndLastName(firstName, lastName);

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

    @Test
    void testChangePassword() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        traineeService.changePassword(traineeId, "newPassword");
        Assertions.assertEquals("newPassword", sampleTrainee.getPassword());
    }

    @Test
    void testActivate() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        sampleTrainee.setIsActive(false);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        traineeService.activate(traineeId);
        Assertions.assertTrue(sampleTrainee.getIsActive());
    }

    @Test
    void testDeactivate() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        traineeService.deactivate(traineeId);
        Assertions.assertFalse(sampleTrainee.getIsActive());
    }

    @Test
    void testAddTrainerToTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setId(2L);

        when(trainerRepository.findById(2L)).thenReturn(Optional.of(trainer));
        when(traineeRepository.save(trainee)).thenAnswer(invocationOnMock -> {
            Trainee trainee1 = invocationOnMock.getArgument(0);
            trainee1.getTrainers().add(trainer);
            return trainee1;
        });
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));

        traineeService.addTrainerToTrainee(2L, 1L);

        Assertions.assertTrue(trainee.getTrainers().contains(trainer));
    }

    @Test
    void testRemoveTrainerFromTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setId(2L);

        trainee.getTrainers().add(trainer);

        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));

        traineeService.removeTrainerFromTrainee(1L, 2L);

        Assertions.assertTrue(trainee.getTrainers().isEmpty());
    }

}
