package com.example.gym_app.service.impl;

import com.example.gym_app.entity.Training;
import com.example.gym_app.repository.TrainingRepository;
import com.example.gym_app.service.TrainingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl extends BaseServiceImpl<Training> implements TrainingService {

    @Override
    public String getCurrentEntityName() {
        return Training.class.getSimpleName();
    }

    public Training create(Training entity) {
        logger.info("Create entity {}", getCurrentEntityName());
        logEntityObject(entity);
        currentRepository.save(entity);
        logger.info("Entity {} successfully saved with id = {}", getCurrentEntityName(), entity.getId());
        return entity;
    }
}

