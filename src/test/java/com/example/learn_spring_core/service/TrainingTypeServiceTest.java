package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
import com.example.learn_spring_core.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

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
        when(trainingTypeRepository.getEntityClass()).thenReturn(TrainingType.class); // Set the entity class to TrainingType
        when(trainingTypeRepository.save(any(TrainingType.class))).thenAnswer(invocation -> {
            TrainingType trainingTypeArgument = invocation.getArgument(0);
            trainingTypeArgument.setId(1L);
            return trainingTypeArgument;
        });
    }

    @Test
    void testCreateTrainingType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("Base trainingType");

        trainingTypeService.create(trainingType);

        Mockito.verify(trainingTypeRepository, times(1)).save(Mockito.any(TrainingType.class));
    }

    @Test
    void testFindAllTrainingTypes() {
        TrainingType trainingType1 = createSampleTrainingType(true);
        TrainingType trainingType2 = new TrainingType();
        trainingType2.setId(2L);
        trainingType2.setTrainingTypeName("Base trainingType 2");
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
        when(trainingTypeRepository.getById(trainingTypeId)).thenReturn(trainingType);

        TrainingType retrievedTrainingType = trainingTypeService.getById(trainingTypeId);

        assertEquals(trainingType, retrievedTrainingType);
        verify(trainingTypeRepository, times(1)).getById(trainingTypeId);
    }

}
