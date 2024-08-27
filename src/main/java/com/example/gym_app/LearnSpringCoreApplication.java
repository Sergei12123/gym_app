package com.example.gym_app;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class LearnSpringCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnSpringCoreApplication.class, args);
    }
}

