package com.example.gym_app.client;

import com.example.gym_app.client.fallback.TrainingItemClientFallback;
import com.example.gym_app.dto.TrainerWorkloadDTO;
import com.example.gym_app.dto.enums.ActionType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "second-service", fallback = TrainingItemClientFallback.class)
public interface TrainingItemClient {

    @PostMapping("/training_item")
    void updateTrainingItem(@RequestHeader("Authorization") String bearerToken,
                            @RequestParam(value = "trainerUserName") final String trainerUserName,
                            @RequestParam(value = "trainerFirstName") final String trainerFirstName,
                            @RequestParam(value = "trainerLastName") final String trainerLastName,
                            @RequestParam(value = "isActive") final boolean isActive,
                            @RequestParam(value = "trainingDate") final LocalDate trainingDate,
                            @RequestParam(value = "trainingDuration") final Long trainingDuration,
                            @RequestParam(value = "actionType") final ActionType actionType);

    @GetMapping("/training_item/trainer")
    TrainerWorkloadDTO getTrainerWorkload(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "trainerUserName") final String trainerUserName);
}
