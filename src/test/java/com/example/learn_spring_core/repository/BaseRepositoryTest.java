package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.configuration.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("classpath:application-test.properties")
@Transactional
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

