package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.component.KeyHolderGenerator;
import com.example.learn_spring_core.repository.entity.Trainee;
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

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraineeRepositoryTest extends TestsParent {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private KeyHolderGenerator keyHolderGenerator;

    @InjectMocks
    private TraineeRepository traineeRepository;

    @BeforeEach
    void setUp() {
        KeyHolder keyHolder = Mockito.mock(KeyHolder.class);
        when(keyHolder.getKey()).thenReturn(1L);
        when(keyHolderGenerator.getNewGeneratedKeyHoler()).thenReturn(keyHolder);
    }

    @Test
    void saveTrainee() {
        Trainee traineeToSave = createSampleTrainee();
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenReturn(1);

        traineeRepository.save(traineeToSave);

        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void updateTrainee() {
        Trainee traineeToUpdate = createSampleTrainee();

        traineeRepository.update(traineeToUpdate);

        verify(jdbcTemplate, times(1)).update(
            TraineeRepository.UPDATE_SQL,
            traineeToUpdate.getDateOfBirth(),
            traineeToUpdate.getAddress(),
            traineeToUpdate.getFirstName(),
            traineeToUpdate.getLastName(),
            traineeToUpdate.getUserName(),
            traineeToUpdate.getPassword(),
            traineeToUpdate.getIsActive() ? 1 : 0,
            traineeToUpdate.getId()
        );
    }

    @Test
    void existsByUsername() {
        String username = "john.doe";
        int expectedCount = 1;
        when(jdbcTemplate.queryForObject(any(String.class), eq(Integer.class), eq(username))).thenReturn(expectedCount);

        traineeRepository.existsByUsername(username);

        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), eq(Integer.class), eq(username));
    }

    @Test
    void findAllTrainee() {
        when(jdbcTemplate.query(any(String.class), any(BeanPropertyRowMapper.class)))
            .thenReturn(Arrays.asList(createSampleTrainee(), new Trainee(), new Trainee()));

        List<Trainee> result = traineeRepository.findAll();

        assertEquals(3, result.size());
        verify(jdbcTemplate, times(1)).query(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void getByIdTrainee() {
        Long traineeId = 1L;
        Trainee expectedTrainee = createSampleTrainee();
        when(jdbcTemplate.queryForObject(any(String.class), any(BeanPropertyRowMapper.class))).thenReturn(expectedTrainee);

        Trainee result = traineeRepository.getById(traineeId);

        assertEquals(expectedTrainee, result);
        verify(jdbcTemplate, times(1)).queryForObject(any(String.class), any(BeanPropertyRowMapper.class));
    }

    @Test
    void deleteByIdTrainee() {
        Long traineeId = 1L;

        traineeRepository.deleteById(traineeId);

        verify(jdbcTemplate, times(1)).update(any(String.class), eq(traineeId));
    }

}

