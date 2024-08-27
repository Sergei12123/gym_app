package com.example.gym_app.repository;

import com.example.gym_app.entity.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.gym_app.utils.SampleCreator.createSampleTrainingType;
import static com.example.gym_app.utils.SampleCreator.createSampleTrainingTypes;
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

        TrainingType foundTraining = trainingTypeRepository.getById(trainingTypeForSave.getId());
        assertEquals(trainingTypeForSave, foundTraining);
    }

    @Test
    void updateTrainingType() {
        TrainingType trainingTypeToUpdate = getSampleTrainingType(true);
        trainingTypeToUpdate.setTrainingTypeName("new TrainingType name");

        trainingTypeRepository.update(trainingTypeToUpdate);
        assertEquals(trainingTypeToUpdate.getTrainingTypeName(), trainingTypeRepository.getById(trainingTypeToUpdate.getId()).getTrainingTypeName());
    }

    @Test
    void findAllTrainingType() {
        List<TrainingType> sampleTrainingTypes = createSampleTrainingTypes(false, 3);
        for (TrainingType trainingType : sampleTrainingTypes) {
            trainingTypeRepository.save(trainingType);
        }

        List<TrainingType> result = trainingTypeRepository.findAll();
        assertEquals(sampleTrainingTypes.size(), result.size());
        assertTrue(sampleTrainingTypes.containsAll(result) && result.containsAll(sampleTrainingTypes));
    }

    @Test
    void getByIdTrainingType() {
        TrainingType trainingTypeForGet = getSampleTrainingType(true);

        TrainingType foundTraining = trainingTypeRepository.getById(trainingTypeForGet.getId());

        assertEquals(trainingTypeForGet, foundTraining);
    }

    @Test
    void deleteByIdTrainingType() {
        TrainingType trainingTypeToDelete = getSampleTrainingType(true);

        trainingTypeRepository.deleteById(trainingTypeToDelete.getId());

        assertNull(trainingTypeRepository.getById(trainingTypeToDelete.getId()));
    }

}

