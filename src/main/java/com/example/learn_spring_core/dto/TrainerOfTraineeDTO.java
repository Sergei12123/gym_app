package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
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
