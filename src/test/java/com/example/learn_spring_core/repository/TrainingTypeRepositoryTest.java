package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainingType;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainingTypes;
import static org.junit.jupiter.api.Assertions.*;

class TrainingTypeRepositoryTest extends BaseRepositoryTest {

    TrainingType getSampleTrainingType(boolean saveRequired) {
        TrainingType sampleTrainingType = createSampleTrainingType(false);

        if (saveRequired) trainingTypeRepository.save(sampleTrainingType);

        return sampleTrainingType;
    }

    @Test
    void saveTrainingType() {
        TrainingType trainingTypeForSave = getSampleTrainingType(false);

        trainingTypeRepository.save(trainingTypeForSave);

        trainingTypeRepository.findById(trainingTypeForSave.getId()).ifPresent(foundTrainingType -> assertEquals(trainingTypeForSave, foundTrainingType));

    }

    @Test
    void updateTrainingType() {
        TrainingType trainingTypeToUpdate = getSampleTrainingType(true);
        trainingTypeToUpdate.setTrainingTypeName(TrainingTypeName.ENDURANCE);

        trainingTypeRepository.save(trainingTypeToUpdate);

        trainingTypeRepository.findById(trainingTypeToUpdate.getId()).map(TrainingType::getTrainingTypeName).ifPresent(trainingTypeName -> assertEquals(trainingTypeToUpdate.getTrainingTypeName(), trainingTypeName));
    }

    @Test
    void findAllTrainingType() {
        List<TrainingType> sampleTrainingTypes = createSampleTrainingTypes(false, 3);
        trainingTypeRepository.saveAll(sampleTrainingTypes);

        List<TrainingType> result = trainingTypeRepository.findAll();
        assertEquals(sampleTrainingTypes.size(), result.size());
        assertTrue(sampleTrainingTypes.containsAll(result) && result.containsAll(sampleTrainingTypes));
    }

    @Test
    void getByIdTrainingType() {
        TrainingType trainingTypeForGet = getSampleTrainingType(true);

        trainingTypeRepository.findById(trainingTypeForGet.getId()).ifPresent(foundTrainingType -> assertEquals(trainingTypeForGet, foundTrainingType));
    }

    @Test
    void deleteByIdTrainingType() {
        TrainingType trainingTypeToDelete = getSampleTrainingType(true);

        trainingTypeRepository.deleteById(trainingTypeToDelete.getId());

        assertTrue(trainingTypeRepository.findById(trainingTypeToDelete.getId()).isEmpty());
    }

}

