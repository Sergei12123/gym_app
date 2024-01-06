package com.example.learn_spring_core.entity;

import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_type")
public class TrainingType extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "training_type_name")
    private TrainingTypeName trainingTypeName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingType trainingType = (TrainingType) o;

        return super.equals(o) &&
            Objects.equals(trainingTypeName, trainingType.trainingTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingTypeName);
    }

}
