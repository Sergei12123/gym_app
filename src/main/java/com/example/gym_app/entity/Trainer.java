package com.example.gym_app.entity;

import com.example.gym_app.entity.enums.TrainingTypeName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainer")
public class Trainer extends User {

    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "training",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private List<Trainee> trainees = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Training> trainings = new ArrayList<>();

    public Trainer(String firstName, String lastName, TrainingTypeName trainingTypeName) {
        super(firstName, lastName);
        this.trainingType = new TrainingType(trainingTypeName);
    }

    public Trainer(String userName, String firstName, String lastName, TrainingType trainingType, boolean isActive) {
        super(userName, firstName, lastName, isActive);
        this.trainingType = trainingType;
    }

    public Trainer(String trainerUserName) {
        super(trainerUserName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Trainer trainer = (Trainer) o;

        return super.equals(o) &&
                Objects.equals(trainingType, trainer.trainingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingType, firstName, lastName, userName, password, isActive);
    }

}
