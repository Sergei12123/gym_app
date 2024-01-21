package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
import com.example.learn_spring_core.service.TrainingTypeService;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeServiceImpl extends BaseServiceImpl<TrainingType> implements TrainingTypeService {

    @Override
    public String getCurrentEntityName() {
        return TrainingType.class.getSimpleName();
    }

    @Override
    public TrainingType findByName(TrainingTypeName trainingTypeName) {
        return ((TrainingTypeRepository) currentRepository).findByTrainingTypeName(trainingTypeName);
    }
}

