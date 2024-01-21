package com.example.learn_spring_core.controller;

import com.example.learn_spring_core.dto.TraineeTrainingProfileDTO;
import com.example.learn_spring_core.dto.TrainerTrainingProfileDTO;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.service.TraineeService;
import com.example.learn_spring_core.service.TrainerService;
import com.example.learn_spring_core.service.TrainingService;
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

    private final TraineeService traineeService;

    private final TrainerService trainerService;

    private final TrainingService trainingService;

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
        if (traineeService.findByUserName(trainerUserName) != null) {
            List<Training> trainingList = trainingService.getTraineeTrainingList(traineeUserName, dateFrom, dateTo, trainerUserName, trainingType);
            return ResponseEntity.ok(trainingList.stream().map(TraineeTrainingProfileDTO::new).toList());
        } else {
            return ResponseEntity.status(404).build();
        }
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
        if (trainerService.findByUserName(trainerUserName) != null) {
            List<Training> trainingList = trainingService.getTrainerTrainingList(trainerUserName, dateFrom, dateTo, traineeUserName);
            return ResponseEntity.ok(trainingList.stream().map(TrainerTrainingProfileDTO::new).toList());
        } else {
            return ResponseEntity.status(404).build();
        }
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
    public ResponseEntity<Object> addTraining(@RequestParam(value = "traineeUserName") final String traineeUserName,
                                              @RequestParam(value = "trainerUserName") final String trainerUserName,
                                              @RequestParam(value = "trainingName") final String trainingName,
                                              @RequestParam(value = "trainingDate") final LocalDate trainingDate,
                                              @RequestParam(value = "trainingDuration") final Long trainingDuration) {
        if (traineeService.findByUserName(traineeUserName) != null && trainerService.findByUserName(trainerUserName) != null) {
            Training training = new Training();
            training.setTrainee(traineeService.findByUserName(traineeUserName));
            training.setTrainer(trainerService.findByUserName(trainerUserName));
            training.setTrainingName(trainingName);
            training.setTrainingDate(trainingDate);
            training.setTrainingDuration(trainingDuration);
            trainingService.create(training);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


}
