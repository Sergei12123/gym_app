package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.repository.entity.Trainee;
import com.example.learn_spring_core.repository.entity.Trainer;
import com.example.learn_spring_core.repository.entity.Training;
import com.example.learn_spring_core.repository.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTraining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingServiceTest extends TestsParent {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        when(trainingRepository.getEntityClass()).thenReturn(Training.class); // Set the entity class to Training
        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
            Training trainingArgument = invocation.getArgument(0);
            trainingArgument.setId(1L);
            return trainingArgument;
        });
    }

    @Test
    void testCreateTraining() {
        Training training = new Training();
        training.setTrainingDate(LocalDate.of(2023, 5, 15));
        training.setTrainingDuration(2L);
        training.setTrainingName("Base training");
        training.setTrainingTypeId(new TrainingType());
        training.setTraineeId(new Trainee());
        training.setTrainerId(new Trainer());

        trainingService.create(training);

        Mockito.verify(trainingRepository, times(1)).save(Mockito.any(Training.class));
    }

    @Test
    void testFindAllTrainings() {
        Training training1 = createSampleTraining();

        Training training2 = new Training();
        training2.setId(2L);
        training2.setTrainingDate(LocalDate.of(2023, 10, 15));
        training2.setTrainingDuration(3L);
        training2.setTrainingName("Base training 2");
        training2.setTrainingTypeId(new TrainingType());
        training2.setTraineeId(new Trainee());
        training2.setTrainerId(new Trainer());
        List<Training> trainings = Arrays.asList(training1, training2);
        when(trainingRepository.findAll()).thenReturn(trainings);

        List<Training> allTrainings = trainingService.findAll();

        assertEquals(trainings, allTrainings);
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void testGetTrainingById() {
        Long trainingId = 1L;
        Training training = createSampleTraining();
        when(trainingRepository.getById(trainingId)).thenReturn(training);

        Training retrievedTraining = trainingService.getById(trainingId);

        assertEquals(training, retrievedTraining);
        verify(trainingRepository, times(1)).getById(trainingId);
    }

}
