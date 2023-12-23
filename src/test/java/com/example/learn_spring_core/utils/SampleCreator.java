package com.example.learn_spring_core.utils;

import com.example.learn_spring_core.repository.entity.Trainee;
import com.example.learn_spring_core.repository.entity.Trainer;
import com.example.learn_spring_core.repository.entity.Training;
import com.example.learn_spring_core.repository.entity.TrainingType;

import java.time.LocalDate;

public class SampleCreator {

    public static TrainingType createSampleTrainingType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("Base trainingType");

        return trainingType;
    }

    public static Trainee createSampleTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        trainee.setAddress("123 Main St");
        trainee.setUserName("john.doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        return trainee;
    }

    public static Trainer createSampleTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setUserName("john.doe");
        trainer.setPassword("password123");
        trainer.setIsActive(true);
        trainer.setTrainingTypeId(createSampleTrainingType());
        return trainer;
    }

    public static Training createSampleTraining() {
        Training training = new Training();
        training.setId(1L);
        training.setTrainingDate(LocalDate.of(2023, 5, 15));
        training.setTrainingDuration(2L);
        training.setTrainingName("Base training");
        training.setTrainingTypeId(createSampleTrainingType());
        training.setTraineeId(createSampleTrainee());
        training.setTrainerId(createSampleTrainer());
        return training;
    }
}
