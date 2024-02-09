package com.example.learn_spring_core.messaging.service.impl;

import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.enums.ActionType;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.messaging.dto.TrainingItemDTO;
import com.example.learn_spring_core.messaging.service.TrainingItemService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.example.learn_spring_core.configuration.RabbitMQConfig.QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD;
import static com.example.learn_spring_core.configuration.RabbitMQConfig.QUEUE_TRAINING_ITEM_UPDATE;

@Service
@AllArgsConstructor
public class TrainingItemServiceImpl implements TrainingItemService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void updateTrainingItem(Training training, ActionType actionType) {
        rabbitTemplate.convertAndSend(QUEUE_TRAINING_ITEM_UPDATE, new TrainingItemDTO(training), message -> {
            message.getMessageProperties().setHeader("actionType", actionType);
            return message;
        });
    }

    @Override
    public TrainerWorkloadDTO getTrainerWorkload(String trainerUserName) {
        return (TrainerWorkloadDTO) rabbitTemplate.convertSendAndReceive(QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD, trainerUserName);
    }
}
