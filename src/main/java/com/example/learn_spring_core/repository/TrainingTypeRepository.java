package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingTypeRepository extends BaseRepository<TrainingType> {

    Optional<TrainingType> findByTrainingTypeName(TrainingTypeName name);
}
