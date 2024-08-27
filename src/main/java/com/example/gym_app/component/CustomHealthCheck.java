package com.example.gym_app.component;

import com.example.gym_app.entity.enums.TrainingTypeName;
import com.example.gym_app.service.TrainingTypeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class CustomHealthCheck extends AbstractHealthIndicator {

    private final TrainingTypeService trainingTypeService;

    @Override
    protected void doHealthCheck(Health.Builder bldr) {
        boolean running = trainingTypeService.findAll().stream()
                .allMatch(el -> Arrays.stream(TrainingTypeName.values()).toList().contains(el.getTrainingTypeName()));
        if (running) {
            bldr.up();
        } else {
            bldr.down();
        }
    }
}
