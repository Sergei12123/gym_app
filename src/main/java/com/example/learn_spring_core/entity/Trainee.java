package com.example.learn_spring_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainee")
public class Trainee extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "training",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    public Trainee(String userName, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        super(userName, firstName, lastName, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Trainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        super(firstName, lastName);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Trainee(String traineeUserName) {
        super(traineeUserName);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Trainee trainee = (Trainee) o;

        return super.equals(o) &&
                Objects.equals(dateOfBirth, trainee.dateOfBirth) &&
                Objects.equals(address, trainee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfBirth, address, firstName, lastName, userName, password, isActive);
    }
}
