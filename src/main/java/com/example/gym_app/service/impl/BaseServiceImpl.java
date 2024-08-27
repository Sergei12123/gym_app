package com.example.gym_app.service.impl;

import com.example.gym_app.entity.BaseEntity;
import com.example.gym_app.exception.EntityToJsonSerializeException;
import com.example.gym_app.repository.BaseRepository;
import com.example.gym_app.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

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
            log.info(jsonEntity);
        } catch (JsonProcessingException e) {
            throw new EntityToJsonSerializeException(e);
        }
    }
}
