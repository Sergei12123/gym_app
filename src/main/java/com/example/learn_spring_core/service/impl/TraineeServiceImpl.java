package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.service.TraineeService;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends UserServiceImpl<Trainee> implements TraineeService {

    public void delete(Long traineId) {
        logger.info("Delete the entity Trainee with id = {}", traineId);
        currentRepository.deleteById(traineId);
    }

}

