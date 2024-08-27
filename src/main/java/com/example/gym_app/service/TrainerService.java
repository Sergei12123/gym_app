package com.example.gym_app.service;

import com.example.gym_app.entity.Trainer;

public interface TrainerService extends UserService<Trainer> {


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
