package com.example.gym_app.controller;

import com.example.gym_app.client.TrainingItemClient;
import com.example.gym_app.dto.TraineeTrainingProfileDTO;
import com.example.gym_app.dto.TrainerTrainingProfileDTO;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.service.TrainingService;
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
@RequestMapping("/training")
@Tag(name = "Training", description = "Training management APIs")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingItemClient trainingItemClient;

    @Operation(summary = "Get trainee training list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TraineeTrainingProfileDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Trainee does not exist with supplied userName",
                    content = @Content)
    })
    @GetMapping("/trainee")
    public ResponseEntity<List<TraineeTrainingProfileDTO>> getTraineeTrainingList(@RequestParam(value = "traineeUserName") final String traineeUserName,
                                                                                  @RequestParam(value = "dateFrom", required = false) final LocalDate dateFrom,
                                                                                  @RequestParam(value = "dateTo", required = false) final LocalDate dateTo,
                                                                                  @RequestParam(value = "trainerUserName", required = false) final String trainerUserName,
                                                                                  @RequestParam(value = "trainingType", required = false) final TrainingType trainingType) {
        return ResponseEntity.ok(
                trainingService.getTraineeTrainingList(traineeUserName, dateFrom, dateTo, trainerUserName, trainingType).stream()
                        .map(TraineeTrainingProfileDTO::new).toList()
        );

    }

    @Operation(summary = "Get trainer training list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrainerTrainingProfileDTO.class)))}),
            @ApiResponse(responseCode = "404",
                    description = "Trainer does not exist with supplied userName",
                    content = @Content)
    })
    @GetMapping("/trainer")
    public ResponseEntity<List<TrainerTrainingProfileDTO>> getTrainerTrainingList(@RequestParam(value = "trainerUserName") final String trainerUserName,
                                                                                  @RequestParam(value = "dateFrom", required = false) final LocalDate dateFrom,
                                                                                  @RequestParam(value = "dateTo", required = false) final LocalDate dateTo,
                                                                                  @RequestParam(value = "traineeUserName", required = false) final String traineeUserName) {

        return ResponseEntity.ok(
                trainingService.getTrainerTrainingList(trainerUserName, dateFrom, dateTo, traineeUserName).stream()
                        .map(TrainerTrainingProfileDTO::new).toList()
        );
    }

    @Operation(summary = "Create new training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Trainer/Trainee does not exist with supplied userName",
                    content = @Content)
    })
    @PostMapping("/add")
    public void addTraining(@RequestParam(value = "traineeUserName") final String traineeUserName,
                            @RequestParam(value = "trainerUserName") final String trainerUserName,
                            @RequestParam(value = "trainingName") final String trainingName,
                            @RequestParam(value = "trainingDate") final LocalDate trainingDate,
                            @RequestParam(value = "trainingDuration") final Long trainingDuration) {
        trainingService.create(new Training(traineeUserName, trainerUserName, trainingName, trainingDate, trainingDuration));
    }


}
