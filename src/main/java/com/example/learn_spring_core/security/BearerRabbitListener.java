package com.example.learn_spring_core.security;

import io.micrometer.tracing.annotation.ContinueSpan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static com.example.learn_spring_core.configuration.RabbitMQConfig.QUEUE_BEARER_CHECK;

@Slf4j
@Service
@AllArgsConstructor
public class BearerRabbitListener {

    private final JwtService jwtService;

    @RabbitListener(queues = QUEUE_BEARER_CHECK)
    public boolean bearerCheck(@Header("bearerToken") String bearerToken) {
        log.info("Check bearer token");
        return jwtService.checkBearer(bearerToken);
    }
}
