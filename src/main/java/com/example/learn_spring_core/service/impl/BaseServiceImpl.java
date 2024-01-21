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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected BaseRepository<T> currentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public T getById(Long entityId) {
        return currentRepository.findById(entityId).orElse(null);
    }

    public List<T> findAll() {
        return currentRepository.findAll();
    }

    public void logEntityObject(T entity) {
        try {
            String jsonEntity = objectMapper.writeValueAsString(entity);
            logger.info(jsonEntity);
        } catch (JsonProcessingException e) {
            throw new EntityToJsonSerializeException(e);
        }
    }
}
