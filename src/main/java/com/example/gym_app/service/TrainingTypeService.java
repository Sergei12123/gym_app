package com.example.gym_app.service;

import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.entity.enums.TrainingTypeName;

public interface TrainingTypeService extends BaseService<TrainingType> {

    /**
     * Finds a TrainingType by its name.
     *
     * @param trainingTypeName the name of the TrainingType
     * @return the found TrainingType, or null if not found
     */
    TrainingType findByName(TrainingTypeName trainingTypeName);

}
