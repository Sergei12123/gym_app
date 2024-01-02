package com.example.learn_spring_core.repository.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.repository.TraineeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeRepositoryImpl extends UserRepositoryImpl<Trainee> implements TraineeRepository {

    @Override
    public Class<Trainee> getEntityClass() {
        return Trainee.class;
    }

}
