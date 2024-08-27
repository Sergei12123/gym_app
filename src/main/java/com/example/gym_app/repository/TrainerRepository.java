package com.example.gym_app.repository;

import com.example.gym_app.entity.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends UserRepository<Trainer> {

    List<Trainer> findByTraineesNullAndIsActiveTrue();

    List<Trainer> findByTrainees_UserNameNotContainingAndIsActiveTrue(String traineeUserName);
}
