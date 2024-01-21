package com.example.learn_spring_core.controller;

import com.example.learn_spring_core.dto.TraineeProfileDTO;
import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.service.TraineeService;
import com.example.learn_spring_core.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainee")
@Tag(name = "Trainee", description = "Trainee management APIs")

public class TraineeController {

    private final TraineeService traineeService;

    private final TrainerService trainerService;

    @Operation(summary = "Register trainee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = UserCredentialsDTO.class))}),
        @ApiResponse(responseCode = "409",
            description = "It is not possible to register a new trainee because a trainer with this data already exists.",
            content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserCredentialsDTO> registerTrainee(@RequestParam(value = "firstName") final String firstName,
                                                              @RequestParam(value = "lastName") final String lastName,
                                                              @RequestParam(value = "dateOfBirth", required = false) final LocalDate dateOfBirth,
                                                              @RequestParam(value = "address", required = false) final String address) {
        if(!trainerService.existByFirstNameAndLastNameActiveUser(firstName, lastName)) {
            Trainee trainee = new Trainee();
            trainee.setFirstName(firstName);
            trainee.setLastName(lastName);
            trainee.setDateOfBirth(dateOfBirth);
            trainee.setAddress(address);

            traineeService.create(trainee);
            return ResponseEntity.ok(UserCredentialsDTO.builder()
                .username(trainee.getUserName())
                .password(trainee.getPassword())
                .build());
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @Operation(summary = "Login trainee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Trainee can't authorize with supplied userName or password",
            content = @Content)
    })
    @GetMapping("/login")
    public ResponseEntity<Object> loginTrainee(@RequestParam(value = "userName") final String userName,
                                               @RequestParam(value = "password") final String password) {
        if (traineeService.login(userName, password)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Change trainee password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Trainee can't authorize with supplied userName or password",
            content = @Content)
    })
    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePasswordTrainee(@RequestParam(value = "userName") final String userName,
                                                        @RequestParam(value = "oldPassword") final String oldPassword,
                                                        @RequestParam(value = "newPassword") final String newPassport) {
        if (traineeService.login(userName, oldPassword)) {
            traineeService.changePassword(traineeService.findByUserName(userName).getId(), newPassport);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Get trainee profile by userName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = TraineeProfileDTO.class))}),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @GetMapping("/profile")
    public ResponseEntity<TraineeProfileDTO> getTraineeProfile(@RequestParam(value = "userName") final String userName) {
        Trainee trainee = traineeService.findByUserName(userName);
        if (trainee != null) {
            return ResponseEntity.ok(new TraineeProfileDTO(trainee));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Update trainee profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = TraineeProfileDTO.class))}),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @PutMapping
    public ResponseEntity<TraineeProfileDTO> updateTraineeProfile(@RequestParam(value = "userName") final String userName,
                                                                  @RequestParam(value = "firstName") final String firstName,
                                                                  @RequestParam(value = "lastName") final String lastName,
                                                                  @RequestParam(value = "dateOfBirth", required = false) final LocalDate dateOfBirth,
                                                                  @RequestParam(value = "address", required = false) final String address,
                                                                  @RequestParam(value = "isActive") final boolean isActive) {
        Trainee trainee = traineeService.findByUserName(userName);
        if (trainee != null) {
            trainee.setFirstName(firstName);
            trainee.setLastName(lastName);
            trainee.setDateOfBirth(dateOfBirth);
            trainee.setAddress(address);
            trainee.setIsActive(isActive);
            traineeService.update(trainee);
            return ResponseEntity.ok(new TraineeProfileDTO(trainee));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Delete trainee by userName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<Object> deleteTrainee(@RequestParam(value = "userName") final String userName) {
        if (traineeService.findByUserName(userName) != null) {
            traineeService.deleteByUserName(userName);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }

    }

    @Operation(summary = "Update trainee trainers list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TrainerOfTraineeDTO.class)))}),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @PutMapping("/trainers")
    public ResponseEntity<List<TrainerOfTraineeDTO>> updateTraineeTrainersList(@RequestParam(value = "userName") final String userName,
                                                                               @RequestParam(value = "trainerUserNames") final List<String> trainerUserNames) {
        Trainee trainee = traineeService.findByUserName(userName);
        if (trainee != null) {
            trainee.getTrainers().stream()
                .filter(trainer -> !trainerUserNames.contains(trainer.getUserName()))
                .forEach(trainer -> traineeService.removeTrainerFromTrainee(trainee.getId(), trainer.getId()));
            trainerUserNames.stream()
                .filter(trainerUserName -> trainee.getTrainers().stream().noneMatch(trainer -> trainer.getUserName().equals(trainerUserName)))
                .forEach(trainerUserName -> traineeService.addTrainerToTrainee(trainee.getId(), trainerService.findByUserName(trainerUserName).getId()));

            return ResponseEntity.ok(trainee.getTrainers().stream().map(TrainerOfTraineeDTO::new).toList());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Set trainee active status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @PatchMapping("/setActive")
    public ResponseEntity<Object> setActiveTrainee(@RequestParam(value = "userName") final String userName,
                                                   @RequestParam(value = "isActive") final boolean isActive) {
        if (traineeService.findByUserName(userName) != null) {
            if (isActive) {
                traineeService.activate(traineeService.findByUserName(userName).getId());
            } else {
                traineeService.deactivate(traineeService.findByUserName(userName).getId());
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
