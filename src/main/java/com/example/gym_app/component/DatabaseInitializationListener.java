package com.example.gym_app.component;

import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.entity.enums.TrainingTypeName;
import com.example.gym_app.repository.TrainingTypeRepository;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializationListener implements ApplicationListener<ApplicationEvent> {

    private final TrainingTypeRepository trainingTypeRepository;

    public DatabaseInitializationListener(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            List<TrainingTypeName> allTrainingTypesFromDb = trainingTypeRepository.findAll().stream().map(TrainingType::getTrainingTypeName).toList();
            Arrays.stream(TrainingTypeName.values())
                    .filter(trainingTypeName -> !allTrainingTypesFromDb.contains(trainingTypeName))
                    .forEach(trainingTypeName -> trainingTypeRepository.save(new TrainingType(trainingTypeName)));
        }
    }
}
