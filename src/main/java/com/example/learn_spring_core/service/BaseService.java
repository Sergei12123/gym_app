package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    /**
     * Retrieves the entity with the specified ID.
     *
     * @param entityId the ID of the entity to retrieve
     * @return the entity with the specified ID, or null if not found
     */
    T getById(Long entityId);

    /**
     * Retrieves all elements from the list.
     *
     * @return a list containing all elements
     */
    List<T> findAll();

    /**
     * Logs the provided entity object.
     *
     * @param entity the entity object to be logged
     */
    void logEntityObject(T entity);

    /**
     * Retrieves the name of the current entity.
     *
     * @return the name of the current entity
     */
    String getCurrentEntityName();

}
