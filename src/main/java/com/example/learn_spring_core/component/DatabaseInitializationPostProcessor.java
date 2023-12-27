package com.example.learn_spring_core.component;

import com.example.learn_spring_core.repository.BaseRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializationPostProcessor implements BeanPostProcessor {

    @Value("${db.initialScriptPath}")
    private String dataFilePath;

    private boolean isInitialized = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseRepository<?> && !isInitialized) {
            ((BaseRepository<?>) bean).initializeDb(dataFilePath);
            isInitialized = true;
        }
        return bean;
    }

}
