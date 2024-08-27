package com.example.gym_app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Training extends BaseEntity {

    private Long traineeId;

    private Long trainerId;

    private String trainingName;

    private Long trainingTypeId;

    private LocalDate trainingDate;

    private Long trainingDuration;

}
