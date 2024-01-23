package com.example.learn_spring_core.service;

import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.entity.Trainee;

import java.util.List;

public interface TraineeService extends UserService<Trainee> {

    /**
     * Deletes a trainee with the specified ID.
     *
     * @param traineeId the ID of the trainee to be deleted
     */
    void delete(Long traineeId);

    /**
     * Add a trainer to a trainee via training table.
     *
     * @param trainerUserName the username of the trainer
     * @param trainee         the trainee object
     */
    void addTrainerToTrainee(String trainerUserName, Trainee trainee);

    /**
     * Removes a trainer from a trainee via training table.
     *
     * @param traineeId id of trainee
     * @param trainerId id of trainer
     */
    void removeTrainerFromTrainee(Long traineeId, Long trainerId);

    /**
     * Updates the list of trainers for a trainee in the system.
     *
     * @param userName         the username of the trainee
     * @param trainerUserNames the list of usernames of the trainers to be added
     * @return the updated list of trainers for the trainee
     */
    List<TrainerOfTraineeDTO> updateTraineeTrainersList(String userName, List<String> trainerUserNames);


}
