package com.example.gym_app.dto;

import com.example.gym_app.entity.Trainer;
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
