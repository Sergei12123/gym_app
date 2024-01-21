package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Training;
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
