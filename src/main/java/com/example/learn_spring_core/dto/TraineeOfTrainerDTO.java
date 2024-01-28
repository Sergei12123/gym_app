package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Trainee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraineeOfTrainerDTO {
    private String userName;
    private String firstName;
    private String lastName;

    public TraineeOfTrainerDTO(Trainee trainee) {
        this.userName = trainee.getUsername();
        this.firstName = trainee.getFirstName();
        this.lastName = trainee.getLastName();
    }
}
