package com.example.gym_app.service.impl;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.service.TrainerService;
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

