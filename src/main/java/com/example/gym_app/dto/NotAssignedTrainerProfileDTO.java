package com.example.gym_app.dto;

import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.enums.TrainingTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotAssignedTrainerProfileDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private TrainingTypeName specialization;
    private boolean isActive;

    public NotAssignedTrainerProfileDTO(Trainer trainer) {
        this.userName = trainer.getUsername();
        this.firstName = trainer.getFirstName();
        this.lastName = trainer.getLastName();
        this.specialization = trainer.getTrainingType().getTrainingTypeName();
        this.isActive = trainer.getIsActive();
    }

}
