package com.example.gym_app.service.impl;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.service.TraineeService;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends UserServiceImpl<Trainee> implements TraineeService {

    private final TrainerRepository trainerRepository;


    public TraineeServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public void delete(Long traineId) {
        logger.info("Delete the entity Trainee with id = {}", traineId);
        currentRepository.deleteById(traineId);
    }

    @Override
    public String getCurrentEntityName() {
        return Trainee.class.getSimpleName();
    }

    public void addTrainerToTrainee(Long trainerId, Long traineeId) {
        Trainee trainee = this.getById(traineeId);
        Trainer trainer = trainerRepository.findById(trainerId).orElse(null);
        if (trainee != null && trainer != null) {
            createSampleTraining(trainer, trainee);
            trainerRepository.save(trainer);
            currentRepository.save(trainee);
        }
    }

    @Override
    public void removeTrainerFromTrainee(Long traineeId, Long trainerId) {
        Trainee trainee = this.getById(traineeId);
        trainee.getTrainers().removeIf(trainer -> trainer.getId().equals(trainerId));
    }
}

