package com.example.learn_spring_core.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainingType extends BaseEntity {

    private String trainingTypeName;

}
