package com.example.learn_spring_core.dto;

import com.example.learn_spring_core.entity.Trainer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainerProfileDTO extends NotAssignedTrainerProfileDTO {

    private List<TraineeOfTrainerDTO> trainees;

    public TrainerProfileDTO(Trainer trainer) {
        super(trainer);
        this.trainees = trainer.getTrainees().stream().map(TraineeOfTrainerDTO::new).toList();
    }

}
