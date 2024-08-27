package com.example.gym_app.security.impl;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final TrainerRepository trainerRepository;

    private final TraineeRepository traineeRepository;

    public UserDetailsServiceImpl(TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Trainer> optionalTrainer = trainerRepository.findByUserName(username);
        if (optionalTrainer.isPresent()) {
            return optionalTrainer.get();
        }

        Optional<Trainee> optionalTrainee = traineeRepository.findByUserName(username);
        if (optionalTrainee.isPresent()) {
            return optionalTrainee.get();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
