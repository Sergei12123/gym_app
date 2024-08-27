package com.example.gym_app.service.impl;

import com.example.gym_app.dto.enums.ActionType;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.messaging.service.TrainingItemService;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.repository.TrainingRepository;
import com.example.gym_app.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.gym_app.service.impl.UserServiceImpl.USER_NOT_FOUND_EX;

@Service
public class TrainingServiceImpl extends BaseServiceImpl<Training> implements TrainingService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    private final TrainingItemService trainingItemService;

    public TrainingServiceImpl(TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository,
                               TrainingItemService trainingItemService) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingItemService = trainingItemService;
    }

    @Override
    public String getCurrentEntityName() {
        return Training.class.getSimpleName();
    }

    @Override
    public List<Training> getAllByTraineeUserNameAndTrainingName(String traineeUserName, String trainingName) {
        return ((TrainingRepository) currentRepository).findByTrainee_UserNameAndTrainingName(traineeUserName, trainingName);
    }

    @Override
    public List<Training> getAllByTrainerUserNameAndTrainingName(String trainerUserName, String trainingName) {
        return ((TrainingRepository) currentRepository).findByTrainer_UserNameAndTrainingName(trainerUserName, trainingName);
    }

    @Override
    public Training create(Training entity) {
        Optional<Trainee> foundTrainee = traineeRepository.findByUserName(entity.getTrainee().getUsername());
        Optional<Trainer> foundTrainer = trainerRepository.findByUserName(entity.getTrainer().getUsername());
        if (foundTrainee.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(entity.getTrainee().getUsername()));
        } else if (foundTrainer.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(entity.getTrainer().getUsername()));
        }
        entity.setTrainer(foundTrainer.get());
        entity.setTrainee(foundTrainee.get());
        currentRepository.save(entity);
        trainingItemService.updateTrainingItem(entity, ActionType.ADD);
        return entity;
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeUserName, LocalDate dateFrom, LocalDate dateTo, String trainerUserName, TrainingType trainingType) {
        if (!traineeRepository.existsByUserName(traineeUserName)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(traineeUserName));
        }
        return filterByTrainingFields(((TrainingRepository) currentRepository).findByTrainee_UserName(traineeUserName), dateFrom, dateTo, null, trainerUserName, trainingType);
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerUserName, LocalDate dateFrom, LocalDate dateTo, String traineeUserName) {
        if (!trainerRepository.existsByUserName(trainerUserName)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(trainerUserName));
        }
        return filterByTrainingFields(((TrainingRepository) currentRepository).findByTrainer_UserName(trainerUserName), dateFrom, dateTo, traineeUserName, null, null);
    }

    private List<Training> filterByTrainingFields(List<Training> trainingFullList, LocalDate dateFrom, LocalDate dateTo, String traineeUserName, String trainerUserName, TrainingType trainingType) {
        return trainingFullList.stream()
                .filter(training -> dateFrom == null || dateTo == null || training.getTrainingDate().isAfter(dateFrom) && training.getTrainingDate().isBefore(dateTo))
                .filter(training -> traineeUserName == null || training.getTrainee().getUsername().equals(traineeUserName))
                .filter(training -> trainerUserName == null || training.getTrainer().getUsername().equals(trainerUserName))
                .filter(training -> trainingType == null || training.getTrainingType().equals(trainingType))
                .toList();
    }

    public void delete(Training entity) {
        trainingItemService.updateTrainingItem(entity, ActionType.DELETE);
    }


}

