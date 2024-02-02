package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.service.TrainerService;
import com.example.learn_spring_core.service.TrainingTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl extends UserServiceImpl<Trainer> implements TrainerService {

    private final TraineeRepository traineeRepository;

    private final TrainingTypeService trainingTypeService;

    public TrainerServiceImpl(TraineeRepository traineeRepository, TrainingTypeService trainingTypeService,
                              TrainingRepository trainingRepository) {
        this.traineeRepository = traineeRepository;
        this.trainingTypeService = trainingTypeService;
        this.trainingRepository = trainingRepository;
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
        if (traineeRepository.existsByUserName(traineeUserName)) {
            return ((TrainerRepository) currentRepository).findByTrainees_UserNameNotContainingAndIsActiveTrue(traineeUserName);
        } else {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(traineeUserName));
        }
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
        traineeRepository.findById(traineeId).ifPresent(trainee -> {
            trainee.getTrainings()
                .stream()
                .filter(training -> training.getTrainer().getId().equals(trainerId))
                .forEach(training -> trainingItemService.updateTrainingItem(training, ActionType.DELETE));
            this.getById(trainerId).getTrainees().remove(trainee);
        });

    }

    @Override
    public UserCredentialsDTO create(Trainer user) {
        user.setTrainingType(trainingTypeService.findByName(user.getTrainingType().getTrainingTypeName()));
        return super.create(user);
    }
}

