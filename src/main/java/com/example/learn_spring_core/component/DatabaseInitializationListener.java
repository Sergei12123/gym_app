package com.example.learn_spring_core.component;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
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
