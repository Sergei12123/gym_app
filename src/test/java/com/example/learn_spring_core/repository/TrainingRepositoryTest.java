package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.component.KeyHolderGenerator;
import com.example.learn_spring_core.repository.entity.Training;
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

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTraining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainingRepositoryTest extends TestsParent {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderGenerator keyHolderGenerator;

    @InjectMocks
    private TrainingRepository trainingRepository;

    @BeforeEach
    void setUp() {
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKey()).thenReturn(1L);
        when(keyHolderGenerator.getNewGeneratedKeyHoler()).thenReturn(keyHolder);
    }

    @Test
    void saveTraining() {
        Training trainingToSave = createSampleTraining();
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        trainingRepository.save(trainingToSave);

        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateTraining() {
        Training trainingToUpdate = createSampleTraining();

        trainingRepository.update(trainingToUpdate);

        verify(jdbcTemplate, times(1)).update(
            TrainingRepository.UPDATE_SQL,
            trainingToUpdate.getTraineeId().getId(),
            trainingToUpdate.getTrainerId().getId(),
            trainingToUpdate.getTrainingName(),
            trainingToUpdate.getTrainingTypeId().getId(),
            trainingToUpdate.getTrainingDate(),
            trainingToUpdate.getTrainingDuration(),
            trainingToUpdate.getId()
        );
    }

    @Test
    void findAllTraining() {
        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
            .thenReturn(Arrays.asList(createSampleTraining(), new Training(), new Training()));

        List<Training> result = trainingRepository.findAll();

        assertEquals(3, result.size());
        verify(jdbcTemplate, times(1)).query(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void getByIdTraining() {
        Long trainingId = 1L;
        Training expectedTraining = createSampleTraining();
        when(jdbcTemplate.queryForObject(any(String.class), any(BeanPropertyRowMapper.class))).thenReturn(expectedTraining);

        Training result = trainingRepository.getById(trainingId);

        assertEquals(expectedTraining, result);
        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void deleteByIdTraining() {
        Long trainingId = 1L;

        trainingRepository.deleteById(trainingId);

        verify(jdbcTemplate, times(1)).update(any(String.class), eq(trainingId));
    }
}

