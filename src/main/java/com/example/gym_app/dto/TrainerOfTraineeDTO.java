package com.example.gym_app.dto;

import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.enums.TrainingTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerOfTraineeDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingTypeName trainingTypeName;

    public TrainerOfTraineeDTO(Trainer trainer) {
        this.userName = trainer.getUserName();
        this.firstName = trainer.getFirstName();
        this.lastName = trainer.getLastName();
        this.trainingTypeName = trainer.getTrainingType().getTrainingTypeName();
    }
}
