package com.example.gym_app.controller;

import com.example.gym_app.dto.TraineeProfileDTO;
import com.example.gym_app.dto.TrainerOfTraineeDTO;
import com.example.gym_app.dto.UserCredentialsDTO;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.service.TraineeService;
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
        return ResponseEntity.ok(traineeService.create(new Trainee(firstName, lastName, dateOfBirth, address)));
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
    public ResponseEntity<String> loginTrainee(@RequestParam(value = "userName") final String userName,
                                               @RequestParam(value = "password") final String password) {

        return ResponseEntity.ok(traineeService.login(userName, password));
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
    public void changePasswordTrainee(@RequestParam(value = "userName") final String userName,
                                      @RequestParam(value = "oldPassword") final String oldPassword,
                                      @RequestParam(value = "newPassword") final String newPassport) {
        traineeService.changePassword(userName, oldPassword, newPassport);
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
        return ResponseEntity.ok(new TraineeProfileDTO(
                traineeService.findByUserName(userName)
        ));
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
        return ResponseEntity.ok(new TraineeProfileDTO(
                traineeService.update(new Trainee(userName, firstName, lastName, dateOfBirth, address, isActive))
        ));

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
    public void deleteTrainee(@RequestParam(value = "userName") final String userName) {
        traineeService.deleteByUserName(userName);
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

        return ResponseEntity.ok(traineeService.updateTraineeTrainersList(userName, trainerUserNames));
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
    public void setActiveTrainee(@RequestParam(value = "userName") final String userName,
                                 @RequestParam(value = "isActive") final boolean isActive) {
        traineeService.setActive(userName, isActive);
    }

}
