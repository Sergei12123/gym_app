package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.BaseEntity;
import com.example.learn_spring_core.exception.EntityToJsonSerializeException;
import com.example.learn_spring_core.repository.BaseRepository;
import com.example.learn_spring_core.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected BaseRepository<T> currentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public T getById(Long entityId) {
        logger.info("Get an entity {} with id = {}", getCurrentEntityName(), entityId);
        return currentRepository.getById(entityId);
    }

    public List<T> findAll() {
        logger.info("Get all entities {}", getCurrentEntityName());
        return currentRepository.findAll();
    }

    public T create(T entity) {
        logger.info("Create entity {}", getCurrentEntityName());
        logEntityObject(entity);
        currentRepository.save(entity);
        logger.info("Entity {} successfully saved with id = {}", getCurrentEntityName(), entity.getId());
        return entity;
    }

    public void logEntityObject(T entity) {
        try {
            String jsonEntity = objectMapper.writeValueAsString(entity);
            logger.info(jsonEntity);
        } catch (JsonProcessingException e) {
            throw new EntityToJsonSerializeException(e);
        }
    }

    public String getCurrentEntityName() {
        return currentRepository.getEntityClass().getSimpleName();
    }
}
