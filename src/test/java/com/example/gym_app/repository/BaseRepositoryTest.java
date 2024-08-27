package com.example.gym_app.repository;

import com.example.gym_app.configuration.TestConfig;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
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
        trainingRepository.findAll().stream().map(Training::getId).forEach(trainingRepository::deleteById);
        trainerRepository.findAll().stream().map(Trainer::getId).forEach(trainerRepository::deleteById);
        trainingTypeRepository.findAll().stream().map(TrainingType::getId).forEach(trainingTypeRepository::deleteById);
        traineeRepository.findAll().stream().map(Trainee::getId).forEach(traineeRepository::deleteById);
    }

}

