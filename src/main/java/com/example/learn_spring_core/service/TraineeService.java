package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.Trainee;

public interface TraineeService extends UserService<Trainee> {

    void delete(Long traineeId);

    void addTrainerToTrainee(Long trainerId, Long traineeId);

    void removeTrainerFromTrainee(Long traineeId, Long trainerId);

}
