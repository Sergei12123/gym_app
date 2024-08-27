package com.example.gym_app.service;

import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService extends BaseService<Training> {

    /**
     * Get all trainings by trainee username and training name
     *
     * @param traineeUserName userName of trainee
     * @param trainingName    name of training
     * @return list of trainings
     */
    List<Training> getAllByTraineeUserNameAndTrainingName(String traineeUserName, String trainingName);

    /**
     * Get all trainings by trainer username and training name
     *
     * @param trainerUserName userName of trainer
     * @param trainingName    name of training
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

    /**
     * Retrieves a list of Training objects based on the specified criteria.
     *
     * @param traineeUserName the name of the trainee
     * @param dateFrom        the start date of the training period
     * @param dateTo          the end date of the training period
     * @param trainerName     the name of the trainer
     * @param trainingType    the type of training
     * @return a list of Training objects that match the criteria
     */
    List<Training> getTraineeTrainingList(String traineeUserName, LocalDate dateFrom, LocalDate dateTo, String trainerName, TrainingType trainingType);

    /**
     * Retrieves a list of Training objects based on the specified criteria.
     *
     * @param trainerUserName the name of the trainer
     * @param dateFrom        the start date of the training period
     * @param dateTo          the end date of the training period
     * @param traineeUserName the name of the trainee
     * @return a list of Training objects that match the criteria
     */
    List<Training> getTrainerTrainingList(String trainerUserName, LocalDate dateFrom, LocalDate dateTo, String traineeUserName);

    /**
     * Delete training
     *
     * @param training entity of training
     */
    void delete(Training training);

}
