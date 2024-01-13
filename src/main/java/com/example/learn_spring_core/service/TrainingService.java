package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Training;

import java.util.List;

public interface TrainingService extends BaseService<Training> {

    /**
     * Get all trainings by trainee username and training name
     *
     * @param traineeUserName userName of trainee
     * @param trainingName name of training
     * @return list of trainings
     */
    List<Training> getAllByTraineeUserNameAndTrainingName(String traineeUserName, String trainingName);

    /**
     * Get all trainings by trainer username and training name
     *
     * @param trainerUserName userName of trainer
     * @param trainingName name of training
     * @return list of trainings
     */
    List<Training> getAllByTrainerUserNameAndTrainingName(String trainerUserName, String trainingName);

    /**
     * Create new training
     *
     * @param training entity of training
     * @return new training with all fields
     */
    Training create(Training training);

}
