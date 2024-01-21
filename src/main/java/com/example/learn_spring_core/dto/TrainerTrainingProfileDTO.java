package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Training;
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
