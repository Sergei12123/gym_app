package com.example.learn_spring_core.security.impl;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
        log.info("trainer all: " + trainerRepository.count());
        if (optionalTrainer.isPresent()) {
            return optionalTrainer.get();
        }

        Optional<Trainee> optionalTrainee = traineeRepository.findByUserName(username);
        log.info("trainee all: " + traineeRepository.count());
        if (optionalTrainee.isPresent()) {
            return optionalTrainee.get();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
