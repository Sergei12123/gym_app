package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.repository.TrainerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerRepositoryImpl extends UserRepositoryImpl<Trainer> implements TrainerRepository {

    @Override
    public Class<Trainer> getEntityClass() {
        return Trainer.class;
    }

}