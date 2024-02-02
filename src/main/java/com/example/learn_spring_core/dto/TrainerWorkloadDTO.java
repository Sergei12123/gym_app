package com.example.learn_spring_core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class TrainerWorkloadDTO {

    private String trainerUserName;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean status;
    private Map<Integer, Map<Integer, Long>> workload;

}
