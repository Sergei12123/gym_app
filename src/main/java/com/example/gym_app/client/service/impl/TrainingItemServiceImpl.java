package com.example.gym_app.client.service.impl;

import com.example.gym_app.client.TrainingItemClient;
import com.example.gym_app.client.service.TrainingItemService;
import com.example.gym_app.dto.TrainerWorkloadDTO;
import com.example.gym_app.dto.enums.ActionType;
import com.example.gym_app.entity.Training;
import com.example.gym_app.repository.TrainerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.gym_app.service.impl.UserServiceImpl.USER_NOT_FOUND_EX;

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
