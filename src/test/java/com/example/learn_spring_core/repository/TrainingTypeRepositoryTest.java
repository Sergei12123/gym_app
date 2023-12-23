package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.component.KeyHolderGenerator;
import com.example.learn_spring_core.repository.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainingType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingTypeRepositoryTest extends TestsParent {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderGenerator keyHolderGenerator;

    @InjectMocks
    private TrainingTypeRepository trainingTypeRepository;

    @BeforeEach
    void setUp() {
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKey()).thenReturn(1L);
        when(keyHolderGenerator.getNewGeneratedKeyHoler()).thenReturn(keyHolder);
    }

    @Test
    void saveTrainingType() {
        TrainingType trainingTypeToSave = createSampleTrainingType();
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        trainingTypeRepository.save(trainingTypeToSave);

        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateTrainingType() {
        TrainingType trainingTypeToUpdate = createSampleTrainingType();

        trainingTypeRepository.update(trainingTypeToUpdate);

        verify(jdbcTemplate, times(1)).update(
            TrainingTypeRepository.UPDATE_SQL,
            trainingTypeToUpdate.getTrainingTypeName(),
            trainingTypeToUpdate.getId()
        );
    }

    @Test
    void findAllTrainingType() {
        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
            .thenReturn(Arrays.asList(createSampleTrainingType(), new TrainingType(), new TrainingType()));

        List<TrainingType> result = trainingTypeRepository.findAll();

        assertEquals(3, result.size());
        verify(jdbcTemplate, times(1)).query(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void getByIdTrainingType() {
        Long trainingTypeId = 1L;
        TrainingType expectedTrainingType = createSampleTrainingType();
        when(jdbcTemplate.queryForObject(any(String.class), any(BeanPropertyRowMapper.class))).thenReturn(expectedTrainingType);

        TrainingType result = trainingTypeRepository.getById(trainingTypeId);

        assertEquals(expectedTrainingType, result);
        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void deleteByIdTrainingType() {
        Long trainingTypeId = 1L;

        trainingTypeRepository.deleteById(trainingTypeId);

        verify(jdbcTemplate, times(1)).update(any(String.class), eq(trainingTypeId));
    }

}

