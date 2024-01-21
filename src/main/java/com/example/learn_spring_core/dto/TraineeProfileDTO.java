package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Trainee;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TraineeProfileDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private boolean isActive;
    private List<TrainerOfTraineeDTO> trainers;

    public TraineeProfileDTO(Trainee trainee) {
        this.firstName = trainee.getFirstName();
        this.lastName = trainee.getLastName();
        this.dateOfBirth = trainee.getDateOfBirth();
        this.address = trainee.getAddress();
        this.isActive = trainee.getIsActive();
        this.trainers = trainee.getTrainers().stream().map(TrainerOfTraineeDTO::new).toList();
    }

}
