package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends BaseRepository<TrainingType> {

    TrainingType findByTrainingTypeName(TrainingTypeName name);
}
