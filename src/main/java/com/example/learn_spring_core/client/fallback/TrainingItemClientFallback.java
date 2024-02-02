package com.example.learn_spring_core.client.fallback;

import com.example.learn_spring_core.client.TrainingItemClient;
import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.time.LocalDate;


@Slf4j
@Component
public class TrainingItemClientFallback implements TrainingItemClient {
    @Override
    public void updateTrainingItem(String bearerToken, String trainerUserName, String trainerFirstName, String trainerLastName, boolean isActive, LocalDate trainingDate, Long trainingDuration, ActionType actionType) {
        log.error("TrainingItemClientFallback: {}", ConnectException.class);
    }

    @Override
    public TrainerWorkloadDTO getTrainerWorkload(String bearerToken, String trainerUserName) {
        log.error("TrainingItemClientFallback: {}", ConnectException.class);
        return null;
    }
}
