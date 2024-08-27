package com.example.gym_app.repository;

import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.entity.enums.TrainingTypeName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingTypeRepository extends BaseRepository<TrainingType> {

    Optional<TrainingType> findByTrainingTypeName(TrainingTypeName name);
}
