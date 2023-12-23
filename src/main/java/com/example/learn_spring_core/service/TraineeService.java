package com.example.learn_spring_core.service;

import com.example.learn_spring_core.repository.entity.Trainee;
import org.springframework.stereotype.Service;

@Service
public class TraineeService extends UserService<Trainee> {

    public void delete(Long traineId) {
        logger.info("Delete the entity Trainee with id = {}", traineId);
        currentRepository.deleteById(traineId);
    }

}

