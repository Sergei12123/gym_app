package com.example.learn_spring_core.service.impl;

import com.example.learn_spring_core.entity.BaseEntity;
import com.example.learn_spring_core.exception.EntityToJsonSerializeException;
import com.example.learn_spring_core.repository.BaseRepository;
import com.example.learn_spring_core.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    protected BaseRepository<T> currentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public T getById(Long entityId) {
        return currentRepository.findById(entityId).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return currentRepository.findAll();
    }

    @Override
    public void logEntityObject(T entity) {
        try {
            String jsonEntity = objectMapper.writeValueAsString(entity);
            log.info(jsonEntity);
        } catch (JsonProcessingException e) {
            throw new EntityToJsonSerializeException(e);
        }
    }

    public String getBearerToken(){
        return SecurityContextHolder.getContext().getAuthentication().getDetails().toString();
    }
}
