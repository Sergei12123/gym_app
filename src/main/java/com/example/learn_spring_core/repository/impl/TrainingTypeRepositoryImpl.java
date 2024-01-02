package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.repository.TrainingTypeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypeRepositoryImpl extends BaseRepositoryImpl<TrainingType> implements TrainingTypeRepository {

    @Override
    public Class<TrainingType> getEntityClass() {
        return TrainingType.class;
    }

}
