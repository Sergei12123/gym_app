package com.example.learn_spring_core.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TransactionIdAspect {

    @Before("execution(* com.example.learn_spring_core.service.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        log.info("Attempting to execute a method {} in the {} class. ",
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("execution(* com.example.learn_spring_core.service.*.*(..))")
    public void afterServiceMethod(JoinPoint joinPoint) {
        log.info("The {} method in the {} class has been successfully executed.. ",
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

}
