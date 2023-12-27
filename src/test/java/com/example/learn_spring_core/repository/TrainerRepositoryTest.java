package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerRepositoryTest extends BaseRepositoryTest {

    Trainer getSampleTrainerForSave(boolean saveRequired) {
        Trainer sampleTrainer = createSampleTrainer(false);

        setForeignKey(sampleTrainer);
        if (saveRequired) trainerRepository.save(sampleTrainer);

        return sampleTrainer;
    }

    private void setForeignKey(Trainer sampleTrainer) {
        TrainingType trainingType = createSampleTrainingType(false);
        trainingTypeRepository.save(trainingType);
        sampleTrainer.setTrainingTypeId(trainingType.getId());
    }

    @Test
    void saveTrainer() {
        Trainer trainerToSave = getSampleTrainerForSave(false);

        trainerRepository.save(trainerToSave);

        Trainer foundTrainer = trainerRepository.getById(trainerToSave.getId());
        assertEquals(trainerToSave, foundTrainer);
    }

    @Test
    void updateTrainer() {
        Trainer trainerToUpdate = getSampleTrainerForSave(true);
        trainerToUpdate.setFirstName("new firstName");

        trainerRepository.update(trainerToUpdate);
        assertEquals(trainerToUpdate.getFirstName(), trainerRepository.getById(trainerToUpdate.getId()).getFirstName());
    }

    @Test
    void existsByUsername() {
        Trainer sampleTrainer = getSampleTrainerForSave(true);

        assertTrue(trainerRepository.existsByUsername(sampleTrainer.getUserName()));
        assertFalse(trainerRepository.existsByUsername(sampleTrainer.getUserName() + "1"));

    }

    @Test
    void findAllTrainer() {
        List<Trainer> sampleTrainers = createSampleTrainers(false, 3);
        for (Trainer trainer : sampleTrainers) {
            setForeignKey(trainer);
            trainerRepository.save(trainer);
        }

        List<Trainer> result = trainerRepository.findAll();

        assertEquals(sampleTrainers.size(), result.size());
        assertTrue(sampleTrainers.containsAll(result) && result.containsAll(sampleTrainers));
    }

    @Test
    void getByIdTrainer() {
        Trainer trainerToGet = getSampleTrainerForSave(true);

        Trainer foundTrainer = trainerRepository.getById(trainerToGet.getId());

        assertEquals(trainerToGet, foundTrainer);
    }

    @Test
    void deleteByIdTrainer() {
        Trainer trainerToDelete = getSampleTrainerForSave(true);

        trainerRepository.deleteById(trainerToDelete.getId());

        assertNull(trainerRepository.getById(trainerToDelete.getId()));
    }

}

