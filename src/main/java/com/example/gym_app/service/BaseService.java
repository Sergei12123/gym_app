package com.example.gym_app.service;

import com.example.gym_app.entity.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    T getById(Long entityId);

    List<T> findAll();

    void logEntityObject(T entity);

    String getCurrentEntityName();

}
