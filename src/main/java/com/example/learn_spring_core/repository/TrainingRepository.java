package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Training;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends BaseRepository<Training> {

    List<Training> findByTrainee_UserNameAndTrainingName(String traineeUserName, String trainingName);

    List<Training> findByTrainer_UserNameAndTrainingName(String trainerUserName, String trainingName);

}
