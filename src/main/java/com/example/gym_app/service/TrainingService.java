package com.example.gym_app.service;

import com.example.gym_app.entity.Training;

public interface TrainingService extends BaseService<Training> {


    /**
     * Create new training
     *
     * @param training entity of training
     * @return new training with all fields
     */
    Training create(Training training);

}
