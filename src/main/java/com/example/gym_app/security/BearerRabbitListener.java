package com.example.gym_app.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static com.example.gym_app.configuration.RabbitMQConfig.QUEUE_BEARER_CHECK;

@Slf4j
@Service
@AllArgsConstructor
public class BearerRabbitListener {

    private final JwtService jwtService;

    @RabbitListener(queues = QUEUE_BEARER_CHECK)
    public boolean bearerCheck(@Payload String bearerToken) {
        log.info("Check bearer token");
        return jwtService.checkBearer(bearerToken);
    }
}
