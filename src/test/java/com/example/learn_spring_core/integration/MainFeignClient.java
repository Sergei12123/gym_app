package com.example.learn_spring_core.integration;


import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "main-service", url = "http://localhost:${local.server.port}")
public interface MainFeignClient {

    @PostMapping("/training/add")
    void addTraining(
        @RequestHeader(value = "Authorization") final String bearerToken,
        @RequestParam(value = "traineeUserName") final String traineeUserName,
        @RequestParam(value = "trainerUserName") final String trainerUserName,
        @RequestParam(value = "trainingName") final String trainingName,
        @RequestParam(value = "trainingDate") final LocalDate trainingDate,
        @RequestParam(value = "trainingDuration") final Long trainingDuration);

    @PostMapping("/trainer/register")
    ResponseEntity<UserCredentialsDTO> registerTrainer(@RequestParam(value = "firstName") final String firstName,
                                                       @RequestParam(value = "lastName") final String lastName,
                                                       @RequestParam(value = "trainingTypeName") final TrainingTypeName trainingTypeName);

    @PostMapping("/trainee/register")
    ResponseEntity<UserCredentialsDTO> registerTrainee(@RequestParam(value = "firstName") final String firstName,
                                                       @RequestParam(value = "lastName") final String lastName,
                                                       @RequestParam(value = "dateOfBirth", required = false) final LocalDate dateOfBirth,
                                                       @RequestParam(value = "address", required = false) final String address);

    @GetMapping("/trainee/login")
    ResponseEntity<String> loginTrainee(@RequestParam(value = "userName") final String userName,
                                        @RequestParam(value = "password") final String password);

    @GetMapping("/trainer/login")
    ResponseEntity<String> loginTrainer(@RequestParam(value = "userName") final String userName,
                                        @RequestParam(value = "password") final String password);

    @GetMapping("/trainer/workload")
    TrainerWorkloadDTO getTrainerWorkload(@RequestHeader(value = "Authorization") final String bearerToken,
                                          @RequestParam(value = "userName") final String userName);

    @DeleteMapping("/trainee")
    void deleteTrainee(@RequestHeader(value = "Authorization") final String bearerToken,
                       @RequestParam(value = "userName") final String userName);

    @PutMapping("/trainee/trainers")
    ResponseEntity<List<TrainerOfTraineeDTO>> updateTraineeTrainersList(@RequestHeader(value = "Authorization") final String bearerToken,
                                                                        @RequestParam(value = "userName") final String userName,
                                                                        @RequestParam(value = "trainerUserNames") final List<String> trainerUserNames);

}
