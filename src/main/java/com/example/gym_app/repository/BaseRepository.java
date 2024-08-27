package com.example.gym_app.repository;

import com.example.gym_app.entity.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity> {

    void initializeDb(String dataFilePath);

    Class<T> getEntityClass();

    T getById(Long entityId);

    List<T> findAll();

    T save(T entity);

    void update(T entity);

    void deleteById(Long entityId);

}
