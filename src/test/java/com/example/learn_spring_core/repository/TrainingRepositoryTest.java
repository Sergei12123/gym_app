package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.Training;
import com.example.learn_spring_core.entity.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainingRepositoryTest extends BaseRepositoryTest {

    Training getSampleTraining(boolean saveRequired) {
        Training sampleTraining = createSampleTraining(false);

        setAllForeignKeys(sampleTraining);
        if (saveRequired) trainingRepository.save(sampleTraining);

        return sampleTraining;
    }

    private void setAllForeignKeys(Training sampleTraining) {
        TrainingType trainingType = createSampleTrainingType(false);
        trainingTypeRepository.save(trainingType);
        sampleTraining.setTrainingType(trainingType);

        Trainer trainer = createSampleTrainer(false);
        trainer.setTrainingType(trainingType);
        trainerRepository.save(trainer);
        sampleTraining.setTrainer(trainer);

        Trainee trainee = createSampleTrainee(false);
        traineeRepository.save(trainee);
        sampleTraining.setTrainee(trainee);
    }

    @Test
    void saveTraining() {
        Training trainingForSave = getSampleTraining(false);

        trainingRepository.save(trainingForSave);

        Training foundTraining = trainingRepository.getById(trainingForSave.getId());
        assertEquals(trainingForSave, foundTraining);
    }

    @Test
    void updateTraining() {
        Training trainingToUpdate = getSampleTraining(true);
        trainingToUpdate.setTrainingName("new training name");

        trainingRepository.update(trainingToUpdate);
        assertEquals(trainingToUpdate.getTrainingName(), trainingRepository.getById(trainingToUpdate.getId()).getTrainingName());
    }

    @Test
    void findAllTraining() {

        List<Training> sampleTrainings = createSampleTrainings(false, 3);
        for (Training training : sampleTrainings) {
            setAllForeignKeys(training);
            trainingRepository.save(training);
        }

        List<Training> result = trainingRepository.findAll();

        assertEquals(sampleTrainings.size(), result.size());
        assertTrue(sampleTrainings.containsAll(result) && result.containsAll(sampleTrainings));
    }

    @Test
    void getByIdTraining() {
        Training trainingForGet = getSampleTraining(true);

        Training foundTraining = trainingRepository.getById(trainingForGet.getId());

        assertEquals(trainingForGet, foundTraining);

    }

    @Test
    void deleteByIdTraining() {
        Training trainingToDelete = getSampleTraining(true);

        trainingRepository.deleteById(trainingToDelete.getId());

        assertNull(trainingRepository.getById(trainingToDelete.getId()));
    }
}

