package com.example.learn_spring_core.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWorkloadDTO {

    private String trainerUserName;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean status;
    private Map<Integer, Map<Integer, Long>> workload;

}
