package com.example.gym_app.dto;

import com.example.gym_app.entity.Training;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeTrainingProfileDTO extends TrainingProfileDTO {

    private String trainerName;

    public TraineeTrainingProfileDTO(Training training) {
        super(training);
        this.trainerName = training.getTrainer().getFirstName();
    }

}
