package com.example.learn_spring_core.service;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;

public interface TrainingTypeService extends BaseService<TrainingType> {

    /**
     * Finds a TrainingType by its name.
     *
     * @param trainingTypeName the name of the TrainingType
     * @return the found TrainingType, or null if not found
     */
    TrainingType findByName(TrainingTypeName trainingTypeName);

}
