package com.example.learn_spring_core.controller;

import com.example.learn_spring_core.dto.NotAssignedTrainerProfileDTO;
import com.example.learn_spring_core.dto.TrainerProfileDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainer")
@Tag(name = "Trainer", description = "Trainer management APIs")
public class TrainerController {

    private final TrainerService trainerService;

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
        return ResponseEntity.ok(trainerService.create(new Trainer(firstName, lastName, trainingTypeName)));
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
    public ResponseEntity<String> loginTrainer(@RequestParam(value = "userName") final String userName,
                                               @RequestParam(value = "password") final String password) {
        return ResponseEntity.ok(trainerService.login(userName, password));
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
    public ResponseEntity<TrainerProfileDTO> getTraineeProfile(@RequestParam(value = "userName") final String userName) {
        return ResponseEntity.ok(new TrainerProfileDTO(
                trainerService.findByUserName(userName)
        ));
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
    public void changePasswordTrainer(@RequestParam(value = "userName") final String userName,
                                      @RequestParam(value = "oldPassword") final String oldPassword,
                                      @RequestParam(value = "newPassport") final String newPassport) {
        trainerService.changePassword(userName, oldPassword, newPassport);
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
        return ResponseEntity.ok(new TrainerProfileDTO(
                trainerService.update(new Trainer(userName, firstName, lastName, trainingType, isActive))
        ));

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
    @GetMapping("/trainers")
    public ResponseEntity<List<NotAssignedTrainerProfileDTO>> getNotAssignedForTraineeTrainers(@RequestParam(value = "traineeUserName") final String traineeUserName) {

        return ResponseEntity.ok(
                trainerService.getNotAssignedToConcreteTraineeActiveTrainers(traineeUserName).stream()
                        .map(NotAssignedTrainerProfileDTO::new).toList()
        );

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
    public void setActiveTrainer(@RequestParam(value = "userName") final String userName,
                                 @RequestParam(value = "isActive") final boolean isActive) {
        trainerService.setActive(userName, isActive);
    }

}
