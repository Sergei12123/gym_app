package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl extends UserServiceImpl<Trainee> implements TraineeService {

    private final TrainerRepository trainerRepository;


    public TraineeServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public void delete(Long traineId) {
        currentRepository.deleteById(traineId);
    }

    @Override
    public String getCurrentEntityName() {
        return Trainee.class.getSimpleName();
    }

    @Override
    public void addTrainerToTrainee(String trainerUserName, @NonNull Trainee trainee) {
        Optional<Trainer> trainerOptional = trainerRepository.findByUserName(trainerUserName);
        if (trainerOptional.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(trainerUserName));
        }
        createSampleTraining(trainerOptional.get(), trainee);
        trainerRepository.save(trainerOptional.get());
        currentRepository.save(trainee);

    }

    @Override
    public void removeTrainerFromTrainee(Long traineeId, Long trainerId) {
        trainerRepository.findById(trainerId).ifPresent(trainer -> {
            trainer.getTrainings()
                .stream()
                .filter(training -> training.getTrainee().getId().equals(traineeId))
                .forEach(training -> trainingService.delete(training));
            this.getById(traineeId).getTrainers().remove(trainer);
        });
    }

    @Override
    public List<TrainerOfTraineeDTO> updateTraineeTrainersList(String userName, List<String> trainerUserNames) {
        Optional<Trainee> trainee = ((TraineeRepository) currentRepository).findByUserName(userName);
        if (trainee.isPresent()) {
            Trainee traineeObject = trainee.get();
            traineeObject.getTrainers().stream()
                .filter(trainer -> !trainerUserNames.contains(trainer.getUsername()))
                .forEach(trainer -> removeTrainerFromTrainee(traineeObject.getId(), trainer.getId()));
            trainerUserNames.stream()
                .filter(trainerUserName -> traineeObject.getTrainers().stream().noneMatch(trainer -> trainer.getUsername().equals(trainerUserName)))
                .forEach(trainerUserName -> addTrainerToTrainee(trainerUserName, traineeObject));
            return traineeObject.getTrainers().stream().map(TrainerOfTraineeDTO::new).toList();
        } else {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(userName));
        }
    }


}

