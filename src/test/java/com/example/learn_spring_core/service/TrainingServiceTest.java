package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTraining;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingServiceTest extends TestsParent {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
            Training trainingArgument = invocation.getArgument(0);
            trainingArgument.setId(1L);
            return trainingArgument;
        });
    }

    @Test
    void testCreateTraining() {
        Training training = createSampleTraining(false);

        trainingService.create(training);

        Mockito.verify(trainingRepository, times(1)).save(Mockito.any(Training.class));
    }

    @Test
    void testFindAllTrainings() {
        List<Training> trainings = createSampleTrainings(true, 3);
        when(trainingRepository.findAll()).thenReturn(trainings);

        List<Training> allTrainings = trainingService.findAll();

        assertEquals(trainings, allTrainings);
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void testGetTrainingById() {
        Long trainingId = 1L;
        Training training = createSampleTraining(true);
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));

        Training retrievedTraining = trainingService.getById(trainingId);

        assertEquals(training, retrievedTraining);
        verify(trainingRepository, times(1)).findById(trainingId);
    }

}
