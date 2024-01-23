package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
import com.example.learn_spring_core.service.TrainingTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeServiceImpl extends BaseServiceImpl<TrainingType> implements TrainingTypeService {


    public static final String TRAINING_TYPE_NOT_FOUND_EX = "TrainingType with name %s not found";

    @Override
    public String getCurrentEntityName() {
        return TrainingType.class.getSimpleName();
    }

    @Override
    public TrainingType findByName(TrainingTypeName trainingTypeName) {
        return ((TrainingTypeRepository) currentRepository).findByTrainingTypeName(trainingTypeName)
            .orElseThrow(() -> new EntityNotFoundException(TRAINING_TYPE_NOT_FOUND_EX.formatted(trainingTypeName.name())));
    }
}

