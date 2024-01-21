package com.example.learn_spring_core.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionIdAspect {

    protected static final Logger logger = LoggerFactory.getLogger(TransactionIdAspect.class);

    @Before("execution(* com.example.learn_spring_core.service.*.*(..))")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        logger.info("Transaction: {}. Attempting to execute a method {} in the {} class. ",
            TransactionIdHolder.getTransactionId(),
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("execution(* com.example.learn_spring_core.service.*.*(..))")
    public void afterServiceMethod(JoinPoint joinPoint) {
        logger.info("Transaction: {}. The {} method in the {} class has been successfully executed.. ",
            TransactionIdHolder.getTransactionId(),
            joinPoint.getSignature().getName(),
            joinPoint.getTarget().getClass().getSimpleName());
    }

}
