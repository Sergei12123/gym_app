package com.example.gym_app.dto;

import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.enums.TrainingTypeName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TrainingProfileDTO {
    private String name;
    private LocalDate date;
    private TrainingTypeName trainingTypeName;
    private Long duration;

    public TrainingProfileDTO(Training training) {
        this.name = training.getTrainingName();
        this.date = training.getTrainingDate();
        this.trainingTypeName = training.getTrainingType().getTrainingTypeName();
        this.duration = training.getTrainingDuration();
    }

}
