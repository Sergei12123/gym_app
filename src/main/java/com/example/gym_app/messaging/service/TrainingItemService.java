package com.example.gym_app.messaging.service;

import com.example.gym_app.dto.TrainerWorkloadDTO;
import com.example.gym_app.dto.enums.ActionType;
import com.example.gym_app.entity.Training;

public interface TrainingItemService {

    /**
     * Updates a training item based on the specified action type.
     *
     * @param training   the training item to be updated
     * @param actionType the type of action to be performed
     */
    void updateTrainingItem(Training training, ActionType actionType);

    /**
     * Returns the workload of the specified trainer.
     *
     * @param trainerUserName the username of the trainer
     * @return the workload of the trainer
     */
    TrainerWorkloadDTO getTrainerWorkload(String trainerUserName);
}
