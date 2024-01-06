package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Trainer;

import java.util.List;

public interface TrainerService extends UserService<Trainer> {

    List<Trainer> getNotAssignedActiveTrainers();

    void addTraineeToTrainer(Long traineeId, Long trainerId);

    void removeTraineeFromTrainer(Long traineeId, Long trainerId);
}
