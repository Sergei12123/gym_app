package com.example.learn_spring_core.controller;

import com.example.learn_spring_core.dto.NotAssignedTrainerProfileDTO;
import com.example.learn_spring_core.dto.TrainerProfileDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.service.TraineeService;
import com.example.learn_spring_core.service.TrainerService;
import com.example.learn_spring_core.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
@Tag(name = "Trainer", description = "Trainer management APIs")
public class TrainerController {

    private final TrainerService trainerService;

    private final TraineeService traineeService;

    private final TrainingTypeService trainingTypeService;

    protected static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @Operation(summary = "Register trainer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = UserCredentialsDTO.class))}),
        @ApiResponse(responseCode = "409",
            description = "It is not possible to register a new trainer because a trainee with this data already exists.",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Unable to find a matching TrainingType with the provided trainingTypeName",
            content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserCredentialsDTO> registerTrainer(@RequestParam(value = "firstName") final String firstName,
                                                              @RequestParam(value = "lastName") final String lastName,
                                                              @RequestParam(value = "trainingTypeName") final TrainingTypeName trainingTypeName) {
        if(!traineeService.existByFirstNameAndLastNameActiveUser(firstName, lastName)) {
            TrainingType trainingType = trainingTypeService.findByName(trainingTypeName);
            if(trainingType!=null) {
                Trainer trainer = new Trainer();
                trainer.setFirstName(firstName);
                trainer.setLastName(lastName);
                trainer.setTrainingType(trainingType);

                trainerService.create(trainer);
                return ResponseEntity.ok(UserCredentialsDTO.builder()
                    .username(trainer.getUserName())
                    .password(trainer.getPassword())
                    .build());
            } else {
                return ResponseEntity.status(404).build();
            }
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @Operation(summary = "Login trainer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Trainer can't authorize with supplied userName or password",
            content = @Content)
    })
    @GetMapping("/login")
    public ResponseEntity<Object> loginTrainer(@RequestParam(value = "userName") final String userName,
                                               @RequestParam(value = "password") final String password) {
        if (trainerService.login(userName, password)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Get trainer profile by userName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = TrainerProfileDTO.class))}),
        @ApiResponse(responseCode = "404",
            description = "Trainer does not exist with supplied userName",
            content = @Content)
    })
    @GetMapping("/profile")
    public ResponseEntity<TrainerProfileDTO> getTraineeProfile(@RequestParam final String userName) {
        Trainer trainer = getTrainerByUserName(userName);
        if (trainer != null) {
            return ResponseEntity.ok(new TrainerProfileDTO(trainer));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Change trainer password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "401",
            description = "Trainer can't authorize with supplied userName or password",
            content = @Content)
    })
    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePasswordTrainer(@RequestParam(value = "userName") final String userName,
                                                        @RequestParam(value = "oldPassword") final String oldPassword,
                                                        @RequestParam(value = "newPassport") final String newPassport) {
        if (trainerService.login(userName, oldPassword)) {
            trainerService.changePassword(getTrainerByUserName(userName).getId(), newPassport);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Update trainer profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = TrainerProfileDTO.class))}),
        @ApiResponse(responseCode = "404",
            description = "Trainer does not exist with supplied userName",
            content = @Content)
    })
    @PutMapping
    public ResponseEntity<TrainerProfileDTO> updateTrainerProfile(@RequestParam(value = "userName") final String userName,
                                                                  @RequestParam(value = "firstName") final String firstName,
                                                                  @RequestParam(value = "lastName") final String lastName,
                                                                  @RequestParam(value = "trainingType") final TrainingType trainingType,
                                                                  @RequestParam(value = "isActive") final boolean isActive) {
        Trainer trainer = getTrainerByUserName(userName);
        if (trainer != null) {
            trainer.setFirstName(firstName);
            trainer.setLastName(lastName);
            trainer.setTrainingType(trainingType);
            trainer.setIsActive(isActive);
            trainerService.update(trainer);
            return ResponseEntity.ok(new TrainerProfileDTO(trainer));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Get not assigned for trainee trainers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = NotAssignedTrainerProfileDTO.class)))}),
        @ApiResponse(responseCode = "404",
            description = "Trainee does not exist with supplied userName",
            content = @Content)
    })
    @PutMapping("/trainers")
    @GetMapping
    public ResponseEntity<List<NotAssignedTrainerProfileDTO>> getNotAssignedForTraineeTrainers(@RequestParam(value = "traineeUserName") final String traineeUserName) {
        if (traineeService.findByUserName(traineeUserName) != null) {
            List<Trainer> trainers = trainerService.getNotAssignedToConcreteTraineeActiveTrainers(traineeUserName);
            return ResponseEntity.ok(trainers.stream().map(NotAssignedTrainerProfileDTO::new).toList());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Set trainer active status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Success",
            content = @Content),
        @ApiResponse(responseCode = "404",
            description = "Trainer does not exist with supplied userName",
            content = @Content)
    })
    @PatchMapping("/setActive")
    public ResponseEntity<Object> setActiveTrainer(@RequestParam final String userName,
                                                   @RequestParam final boolean isActive) {
        if (getTrainerByUserName(userName) != null) {
            if (isActive) {
                trainerService.activate(getTrainerByUserName(userName).getId());
            } else {
                trainerService.deactivate(getTrainerByUserName(userName).getId());
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    public Trainer getTrainerByUserName(final String userName) {
        return trainerService.findByUserName(userName);
    }

}
