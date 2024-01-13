package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Trainer;

import java.util.List;

public interface TrainerService extends UserService<Trainer> {

    /**
     * Retrieves a list of active trainers who are not currently assigned to any trainee.
     *
     * @return  A list of Trainer objects representing the not assigned active trainers.
     */
    List<Trainer> getNotAssignedActiveTrainers();

    /**
     * Adds a trainee to a trainer via training table.
     *
     * @param traineeId id of trainee
     * @param trainerId id of trainer
     */
    void addTraineeToTrainer(Long traineeId, Long trainerId);

    /**
     * Removes a trainee from a trainer via training table.
     *
     * @param traineeId id of trainee
     * @param trainerId id of trainer
     */
    void removeTraineeFromTrainer(Long traineeId, Long trainerId);
}
