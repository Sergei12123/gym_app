package com.example.gym_app.messaging.dto;

import com.example.gym_app.entity.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TrainingItemDTO {

    private String trainerUserName;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private Long trainingDuration;

    public TrainingItemDTO(Training training) {
        this.trainerUserName = training.getTrainer().getUserName();
        this.trainerFirstName = training.getTrainer().getFirstName();
        this.trainerLastName = training.getTrainer().getLastName();
        this.isActive = training.getTrainer().getIsActive();
        this.trainingDate = training.getTrainingDate();
        this.trainingDuration = training.getTrainingDuration();
    }
}
