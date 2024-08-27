package com.example.gym_app;

import com.example.gym_app.configuration.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LearnSpringCoreApplication {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
