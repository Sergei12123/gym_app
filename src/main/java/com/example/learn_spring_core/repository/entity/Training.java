package com.example.learn_spring_core.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Training extends BaseEntity {

    private Trainee traineeId;

    private Trainer trainerId;

    private String trainingName;

    private TrainingType trainingTypeId;

    private LocalDate trainingDate;

    private Long trainingDuration;

}
