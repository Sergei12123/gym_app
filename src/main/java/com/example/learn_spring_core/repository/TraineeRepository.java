package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainee;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends UserRepository<Trainee> {

}
