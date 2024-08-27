package com.example.gym_app.service;

import com.example.gym_app.entity.Training;

public interface TrainingService extends BaseService<Training> {

    Training create(Training training);

}
