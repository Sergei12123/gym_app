package com.example.learn_spring_core.configuration;

import com.example.learn_spring_core.messaging.GlobalBeforePublishMessagePostProcessor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    public static final String QUEUE_MESSAGES_DLQ = "messages.dlq";

    public static final String QUEUE_BEARER_CHECK = "bearer.check";

    public static final String QUEUE_TRAINING_ITEM_UPDATE = "training.item.update";

    public static final String QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD = "training.item.trainer.workload";
    private static final String MAIN_DLX_EXCHANGE = "main.dlx.exchange";

    private final CachingConnectionFactory cachingConnectionFactory;

    private final GlobalBeforePublishMessagePostProcessor globalBeforePublishMessagePostProcessor;

    @Bean
    public Queue bearerCheckQueue() {
        return QueueBuilder.durable(QUEUE_BEARER_CHECK)
            .deadLetterExchange(MAIN_DLX_EXCHANGE)
            .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(MAIN_DLX_EXCHANGE);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(dlqQueue()).to(deadLetterExchange());
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setBeforePublishPostProcessors(globalBeforePublishMessagePostProcessor);
        rabbitTemplate.setObservationEnabled(true);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
            new ConditionalRejectingErrorHandler.DefaultExceptionStrategy() {
                @Override
                public boolean isFatal(@NonNull Throwable t) {
                    return true;
                }
            }));
        factory.setObservationEnabled(true);
        return factory;
    }

}
