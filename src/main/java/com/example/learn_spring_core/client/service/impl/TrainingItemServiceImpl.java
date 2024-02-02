package com.example.learn_spring_core.client.service.impl;

import com.example.learn_spring_core.client.TrainingItemClient;
import com.example.learn_spring_core.client.service.TrainingItemService;
import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.learn_spring_core.service.impl.UserServiceImpl.USER_NOT_FOUND_EX;

@Service
@Transactional
@AllArgsConstructor
public class TrainingItemServiceImpl implements TrainingItemService {

    private final TrainingItemClient trainingItemClient;

    private final TrainerRepository trainerRepository;

    @Override
    public void updateTrainingItem(Training training, ActionType actionType) {
        trainingItemClient.updateTrainingItem(
            getBearerToken(),
            training.getTrainer().getUsername(),
            training.getTrainer().getFirstName(),
            training.getTrainer().getLastName(),
            training.getTrainer().getIsActive(),
            training.getTrainingDate(),
            training.getTrainingDuration(),
            actionType
        );
    }

    @Override
    public TrainerWorkloadDTO getTrainerWorkload(String trainerUserName) {
        if (trainerRepository.existsByUserName(trainerUserName)) {
            return trainingItemClient.getTrainerWorkload(getBearerToken(), trainerUserName);
        }
        throw new EntityNotFoundException(USER_NOT_FOUND_EX.formatted(trainerUserName));
    }

    private static String getBearerToken() {
        return SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
    }
}
