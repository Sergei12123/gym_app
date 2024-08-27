package com.example.gym_app.dto;

import com.example.gym_app.entity.Training;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerTrainingProfileDTO extends TrainingProfileDTO {

    private String traineeName;

    public TrainerTrainingProfileDTO(Training training) {
        super(training);
        this.traineeName = training.getTrainee().getFirstName();
    }

}
