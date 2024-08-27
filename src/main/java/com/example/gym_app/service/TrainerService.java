package com.example.gym_app.service;

import com.example.gym_app.entity.Trainer;

public interface TrainerService extends UserService<Trainer> {

    void addTraineeToTrainer(Long traineeId, Long trainerId);

    void removeTraineeFromTrainer(Long traineeId, Long trainerId);
}
