package com.example.learn_spring_core;

import com.example.learn_spring_core.configuration.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LearnSpringCoreApplication {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
