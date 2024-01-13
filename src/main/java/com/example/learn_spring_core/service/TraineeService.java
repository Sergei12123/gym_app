package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Trainee;

public interface TraineeService extends UserService<Trainee> {

    /**
     * Deletes a trainee with the specified ID.
     *
     * @param  traineeId  the ID of the trainee to be deleted
     */
    void delete(Long traineeId);

    /**
     * Adds a trainer to a trainee via training table.
     *
     * @param trainerId id of trainer
     * @param traineeId id of trainee
     */
    void addTrainerToTrainee(Long trainerId, Long traineeId);

    /**
     * Removes a trainer from a trainee via training table.
     *
     * @param traineeId id of trainee
     * @param trainerId id of trainer
     */
    void removeTrainerFromTrainee(Long traineeId, Long trainerId);

}
