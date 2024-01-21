package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.service.TrainingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public List<Training> getTraineeTrainingList(String traineeUserName, LocalDate dateFrom, LocalDate dateTo, String trainerUserName, TrainingType trainingType) {
        return filterByTrainingFields(((TrainingRepository) currentRepository).findByTrainee_UserName(traineeUserName), dateFrom, dateTo, null, trainerUserName, trainingType);
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerUserName, LocalDate dateFrom, LocalDate dateTo, String traineeUserName) {
        return filterByTrainingFields(((TrainingRepository) currentRepository).findByTrainer_UserName(trainerUserName), dateFrom, dateTo, traineeUserName, null, null);
    }

    private List<Training> filterByTrainingFields(List<Training> trainingFullList, LocalDate dateFrom, LocalDate dateTo, String traineeUserName, String trainerUserName, TrainingType trainingType) {
        return trainingFullList.stream()
            .filter(training -> dateFrom == null || dateTo == null || training.getTrainingDate().isAfter(dateFrom) && training.getTrainingDate().isBefore(dateTo))
            .filter(training -> traineeUserName == null || training.getTrainee().getUserName().equals(traineeUserName))
            .filter(training -> trainerUserName == null || training.getTrainer().getUserName().equals(trainerUserName))
            .filter(training -> trainingType == null || training.getTrainingType().equals(trainingType))
            .toList();
    }


}

