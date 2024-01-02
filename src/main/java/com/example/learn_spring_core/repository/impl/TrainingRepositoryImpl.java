package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.repository.TrainingRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepositoryImpl extends BaseRepositoryImpl<Training> implements TrainingRepository {

    @Override
    public Class<Training> getEntityClass() {
        return Training.class;
    }

}
