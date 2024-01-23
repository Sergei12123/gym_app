package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.repository.TrainingRepository;
import com.example.learn_spring_core.service.TrainingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.service.impl.UserServiceImpl.USER_NOT_FOUND_EX;

@Service
public class TrainingServiceImpl extends BaseServiceImpl<Training> implements TrainingService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public TrainingServiceImpl(TraineeRepository traineeRepository,
                               TrainerRepository trainerRepository) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
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

    public Training create(Training entity) {
        Optional<Trainee> foundTrainee = traineeRepository.findByUserName(entity.getTrainee().getUserName());
        Optional<Trainer> foundTrainer = trainerRepository.findByUserName(entity.getTrainer().getUserName());
        if (foundTrainee.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(entity.getTrainee().getUserName()));
        } else if (foundTrainer.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(entity.getTrainer().getUserName()));
        }
        entity.setTrainer(foundTrainer.get());
        entity.setTrainee(foundTrainee.get());
        logger.info("Create entity {}", getCurrentEntityName());
        logEntityObject(entity);
        currentRepository.save(entity);
        logger.info("Entity {} successfully saved with id = {}", getCurrentEntityName(), entity.getId());
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
            .filter(training -> traineeUserName == null || training.getTrainee().getUserName().equals(traineeUserName))
            .filter(training -> trainerUserName == null || training.getTrainer().getUserName().equals(trainerUserName))
            .filter(training -> trainingType == null || training.getTrainingType().equals(trainingType))
            .toList();
    }


}

