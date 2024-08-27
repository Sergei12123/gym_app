package com.example.gym_app.repository;

import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.gym_app.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        trainingRepository.findById(trainingForSave.getId()).ifPresent(foundTraining -> assertEquals(trainingForSave, foundTraining));
    }

    @Test
    void updateTraining() {
        Training trainingToUpdate = getSampleTraining(true);

        trainingToUpdate.setTrainingName("new training name");
        trainingRepository.save(trainingToUpdate);
        trainingRepository.findById(trainingToUpdate.getId()).map(Training::getTrainingName).ifPresent(trainingName -> assertEquals(trainingToUpdate.getTrainingName(), trainingName));
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

        trainingRepository.findById(trainingForGet.getId()).ifPresent(foundTraining -> assertEquals(trainingForGet, foundTraining));

    }

    @Test
    void deleteByIdTraining() {
        Training trainingToDelete = getSampleTraining(true);
        trainingRepository.deleteById(trainingToDelete.getId());

        assertTrue(trainingRepository.findById(trainingToDelete.getId()).isEmpty());
    }

    @Test
    void findByTrainee_UserNameAndTrainingName() {
        Training training = getSampleTraining(true);

        List<Training> result = trainingRepository.findByTrainee_UserNameAndTrainingName(training.getTrainee().getUserName(), training.getTrainingName());

        assertTrue(result.contains(training));
    }

    @Test
    void findByTrainer_UserNameAndTrainingName() {
        Training training = getSampleTraining(true);

        List<Training> result = trainingRepository.findByTrainer_UserNameAndTrainingName(training.getTrainer().getUserName(), training.getTrainingName());

        assertTrue(result.contains(training));
    }

    @Test
    void findByTrainee_UserName() {
        Training training = getSampleTraining(true);

        List<Training> result = trainingRepository.findByTrainee_UserName(training.getTrainee().getUserName());

        assertTrue(result.contains(training));
    }

    @Test
    void findByTrainer_UserName() {
        Training training = getSampleTraining(true);

        List<Training> result = trainingRepository.findByTrainer_UserName(training.getTrainer().getUserName());

        assertTrue(result.contains(training));
    }


}

