package com.example.learn_spring_core.utils;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleCreator {

    public static TrainingType createSampleTrainingType(final boolean setId) {
        TrainingType trainingType = new TrainingType();
        if (setId) {
            trainingType.setId(1L);
        }
        trainingType.setTrainingTypeName(TrainingTypeName.CARDIO);
        return trainingType;
    }

    public static List<TrainingType> createSampleTrainingTypes(boolean setId, int count){
        final List<TrainingType> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            TrainingType trainingType = createSampleTrainingType(setId);
            result.add(trainingType);
        }
        return result;
    }

    public static Trainee createSampleTrainee(boolean setId) {
        Trainee trainee = new Trainee();
        if (setId) {
            trainee.setId(1L);
        }
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 5, 15));
        trainee.setAddress("123 Main St");
        trainee.setUserName("John.Doe");
        trainee.setPassword("password123");
        trainee.setIsActive(true);
        return trainee;
    }

    public static List<Trainee> createSampleTrainees(boolean setId, int count) {
        final List<Trainee> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Trainee trainee = createSampleTrainee(setId);
            if (setId) {
                trainee.setId((long) i);
            }
            result.add(trainee);
        }
        return result;
    }

    public static Trainer createSampleTrainer(boolean setId) {
        Trainer trainer = new Trainer();
        if (setId) {
            trainer.setId(1L);
        }
        trainer.setFirstName("John");
        trainer.setLastName("Doe");
        trainer.setUserName("john.doe");
        trainer.setPassword("password123");
        trainer.setIsActive(true);
        return trainer;
    }

    public static List<Trainer> createSampleTrainers(boolean setId, int count) {
        final List<Trainer> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Trainer trainer = createSampleTrainer(setId);
            if (setId) {
                trainer.setId((long) i);
                trainer.setTrainingType(new TrainingType());
            }
            result.add(trainer);
        }
        return result;
    }


    public static Training createSampleTraining(boolean setId) {
        Training training = new Training();
        if (setId) {
            training.setId(1L);
        }
        training.setTrainingDate(LocalDate.of(2023, 5, 15));
        training.setTrainingDuration(2L);
        training.setTrainingName("Base training");
        return training;
    }

    public static List<Training> createSampleTrainings(boolean setId, int count){
        final List<Training> result = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Training training = createSampleTraining(setId);
            if (setId) {
                training.setId((long) i);
                training.setTrainingType(new TrainingType());
                training.setTrainee(new Trainee());
                training.setTrainer(new Trainer());
            }
            result.add(training);
        }
        return result;
    }
}
