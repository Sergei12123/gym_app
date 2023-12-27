package com.example.learn_spring_core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Trainer extends User {

    private Long trainingTypeId;

}
