package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends UserRepository<Trainer> {

    List<Trainer> findByTraineesNullAndIsActiveTrue();

}
