package com.example.gym_app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainingType extends BaseEntity {

    private String trainingTypeName;

}
