package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.service.TrainerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl extends UserServiceImpl<Trainer> implements TrainerService {

    private final TraineeRepository traineeRepository;

    public TrainerServiceImpl(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    @Override
    public String getCurrentEntityName() {
        return Trainer.class.getSimpleName();
    }

    @Override
    public List<Trainer> getNotAssignedActiveTrainers() {
        return ((TrainerRepository) currentRepository).findByTraineesNullAndIsActiveTrue();
    }

    @Override
    public List<Trainer> getNotAssignedToConcreteTraineeActiveTrainers(String traineeUserName) {
        return ((TrainerRepository) currentRepository).findByTrainees_UserNameNotContainingAndIsActiveTrue(traineeUserName);
    }

    @Override
    public void addTraineeToTrainer(Long traineeId, Long trainerId) {
        Trainer trainer = this.getById(trainerId);
        Trainee trainee = traineeRepository.findById(traineeId).orElse(null);
        if (trainee != null && trainer != null) {
            createSampleTraining(trainer, trainee);
            currentRepository.save(trainer);
            traineeRepository.save(trainee);
        }
    }


    @Override
    public void removeTraineeFromTrainer(Long trainerId, Long traineeId) {
        Trainer trainer = this.getById(trainerId);
        trainer.getTrainees().removeIf(trainee -> trainee.getId().equals(traineeId));
    }
}

