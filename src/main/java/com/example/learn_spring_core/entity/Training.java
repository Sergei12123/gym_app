package com.example.learn_spring_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training")
@Entity
public class Training extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;

    @Column(name = "training_duration", nullable = false)
    private Long trainingDuration;

    public Training(String traineeUserName, String trainerUserName, String trainingName, LocalDate trainingDate, Long trainingDuration) {
        this.trainee = new Trainee(traineeUserName);
        this.trainer = new Trainer(trainerUserName);
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Training training = (Training) o;

        return super.equals(o) &&
            Objects.equals(trainee, training.trainee) &&
            Objects.equals(trainer, training.trainer) &&
            Objects.equals(trainingName, training.trainingName) &&
            Objects.equals(trainingType, training.trainingType) &&
            Objects.equals(trainingDate, training.trainingDate) &&
            Objects.equals(trainingDuration, training.trainingDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainee, trainer, trainingName, trainingType, trainingDate, trainingDuration);
    }

}
