package com.example.learn_spring_core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;




@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_type")
public class TrainingType extends BaseEntity {

    @Column(name = "training_type_name")
    private String trainingTypeName;

}
