package com.example.gym_app.service;

import com.example.gym_app.entity.Trainee;

public interface TraineeService extends UserService<Trainee> {

    void delete(Long traineeId);

}
