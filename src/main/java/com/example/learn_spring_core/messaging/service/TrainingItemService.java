package com.example.learn_spring_core.messaging.service;

import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import com.example.learn_spring_core.entity.Training;

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
