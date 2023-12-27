package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    T getById(Long entityId);

    List<T> findAll();

    T create(T entity);

    void logEntityObject(T entity);

    String getCurrentEntityName();

}
