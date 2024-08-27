package com.example.gym_app.repository;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DataJpaTest
@ActiveProfiles("test")
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

