package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Training;

import java.util.List;

public interface TrainingService extends BaseService<Training> {

    List<Training> getAllByTraineeUserNameAndTrainingName(String traineeUserName, String trainingName);

    List<Training> getAllByTrainerUserNameAndTrainingName(String trainerUserName, String trainingName);

    Training create(Training training);

}
