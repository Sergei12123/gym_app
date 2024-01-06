package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.service.TrainingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl extends BaseServiceImpl<Training> implements TrainingService {

    @Override
    public String getCurrentEntityName() {
        return Training.class.getSimpleName();
    }

    @Override
    public List<Training> getAllByTraineeUserNameAndTrainingName(String traineeUserName, String trainingName) {
        return ((TrainingRepository) currentRepository).findByTrainee_UserNameAndTrainingName(traineeUserName, trainingName);
    }

    @Override
    public List<Training> getAllByTrainerUserNameAndTrainingName(String trainerUserName, String trainingName) {
        return ((TrainingRepository) currentRepository).findByTrainer_UserNameAndTrainingName(trainerUserName, trainingName);
    }

    public Training create(Training entity) {
        logger.info("Create entity {}", getCurrentEntityName());
        logEntityObject(entity);
        currentRepository.save(entity);
        logger.info("Entity {} successfully saved with id = {}", getCurrentEntityName(), entity.getId());
        return entity;
    }
}

