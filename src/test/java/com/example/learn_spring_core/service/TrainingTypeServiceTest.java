package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
import com.example.learn_spring_core.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainingType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingTypeServiceTest extends TestsParent {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    void setUp() {
        when(trainingTypeRepository.save(any(TrainingType.class))).thenAnswer(invocation -> {
            TrainingType trainingTypeArgument = invocation.getArgument(0);
            trainingTypeArgument.setId(1L);
            return trainingTypeArgument;
        });
    }

    @Test
    void testFindAllTrainingTypes() {
        TrainingType trainingType1 = createSampleTrainingType(true);
        TrainingType trainingType2 = new TrainingType();
        trainingType2.setId(2L);
        trainingType2.setTrainingTypeName(TrainingTypeName.FLEXIBILITY);
        List<TrainingType> trainingTypes = Arrays.asList(trainingType1, trainingType2);
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);

        List<TrainingType> allTrainingTypes = trainingTypeService.findAll();

        assertEquals(trainingTypes, allTrainingTypes);
        verify(trainingTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetTrainingTypeById() {
        Long trainingTypeId = 1L;
        TrainingType trainingType = createSampleTrainingType(true);
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));

        TrainingType retrievedTrainingType = trainingTypeService.getById(trainingTypeId);

        assertEquals(trainingType, retrievedTrainingType);
        verify(trainingTypeRepository, times(1)).findById(trainingTypeId);
    }

    @Test
    void testFindByName() {
        TrainingTypeName trainingTypeName = TrainingTypeName.CARDIO;
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName(trainingTypeName);

        when(trainingTypeRepository.findByTrainingTypeName(trainingTypeName)).thenReturn(Optional.of(trainingType));

        TrainingType result = trainingTypeService.findByName(trainingTypeName);

        Assertions.assertEquals(trainingType, result);
        verify(trainingTypeRepository, times(1)).findByTrainingTypeName(trainingTypeName);

    }

}
