package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.component.KeyHolderGenerator;
import com.example.learn_spring_core.repository.entity.Trainer;
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

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainerRepositoryTest extends TestsParent {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderGenerator keyHolderGenerator;

    @InjectMocks
    private TrainerRepository trainerRepository;

    @BeforeEach
    void setUp() {
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKey()).thenReturn(1L);
        when(keyHolderGenerator.getNewGeneratedKeyHoler()).thenReturn(keyHolder);
    }

    @Test
    void saveTrainer() {
        Trainer trainerToSave = createSampleTrainer();
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        trainerRepository.save(trainerToSave);

        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateTrainer() {
        Trainer trainerToUpdate = createSampleTrainer();

        trainerRepository.update(trainerToUpdate);

        verify(jdbcTemplate, times(1)).update(
            TrainerRepository.UPDATE_SQL,
            trainerToUpdate.getTrainingTypeId().getId(),
            trainerToUpdate.getFirstName(),
            trainerToUpdate.getLastName(),
            trainerToUpdate.getUserName(),
            trainerToUpdate.getPassword(),
            trainerToUpdate.getIsActive() ? 1 : 0,
            trainerToUpdate.getId()
        );
    }

    @Test
    void existsByUsername() {
        String username = "john.doe";
        int expectedCount = 1;
        when(jdbcTemplate.queryForObject(any(String.class), eq(Integer.class), eq(username))).thenReturn(expectedCount);

        trainerRepository.existsByUsername(username);

        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), eq(Integer.class), eq(username));
    }

    @Test
    void findAllTrainer() {
        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
            .thenReturn(Arrays.asList(createSampleTrainer(), new Trainer(), new Trainer()));

        List<Trainer> result = trainerRepository.findAll();

        assertEquals(3, result.size());
        verify(jdbcTemplate, times(1)).query(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void getByIdTrainer() {
        Long trainerId = 1L;
        Trainer expectedTrainer = createSampleTrainer();
        when(jdbcTemplate.queryForObject(any(String.class), any(BeanPropertyRowMapper.class))).thenReturn(expectedTrainer);

        Trainer result = trainerRepository.getById(trainerId);

        assertEquals(expectedTrainer, result);
        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void deleteByIdTrainer() {
        Long trainerId = 1L;

        trainerRepository.deleteById(trainerId);

        verify(jdbcTemplate, times(1)).update(any(String.class), eq(trainerId));
    }
}

