package com.example.gym_app.messaging;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GlobalBeforePublishMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setHeader("bearerToken", SecurityContextHolder.getContext().getAuthentication().getDetails());
        return message;
    }
}

