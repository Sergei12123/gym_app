package com.example.gym_app.service;

import com.example.gym_app.entity.BaseEntity;

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
     * Retrieves the name of the current entity.
     *
     * @return the name of the current entity
     */
    String getCurrentEntityName();

}
