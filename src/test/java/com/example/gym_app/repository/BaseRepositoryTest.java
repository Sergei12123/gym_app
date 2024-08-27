package com.example.gym_app.repository;

import com.example.gym_app.repository.configuration.RepositoryTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
abstract class BaseRepositoryTest {

    @Autowired
    protected TraineeRepository traineeRepository;

    @Autowired
    protected TrainingTypeRepository trainingTypeRepository;

    @Autowired
    protected TrainerRepository trainerRepository;

    @Autowired
    protected TrainingRepository trainingRepository;

    @AfterEach
    void tearDown() {
        trainerRepository.deleteAllInBatch();
        traineeRepository.deleteAllInBatch();
        trainingRepository.deleteAllInBatch();
    }

}

