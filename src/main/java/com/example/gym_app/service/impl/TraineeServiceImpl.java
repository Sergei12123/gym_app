package com.example.gym_app.service.impl;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.service.TraineeService;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl extends UserServiceImpl<Trainee> implements TraineeService {

    public void delete(Long traineId) {
        logger.info("Delete the entity Trainee with id = {}", traineId);
        currentRepository.deleteById(traineId);
    }

}

