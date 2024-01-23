package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.entity.TrainingType;
import com.example.learn_spring_core.service.TrainerService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainerRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private EntityManager entityManager;

    public Trainer getSampleTrainerForSave(boolean saveRequired) {
        Trainer sampleTrainer = createSampleTrainer(false);

        setForeignKey(sampleTrainer);
        if (saveRequired) trainerRepository.save(sampleTrainer);

        return sampleTrainer;
    }

    private void setForeignKey(Trainer sampleTrainer) {
        TrainingType trainingType = createSampleTrainingType(false);
        trainingTypeRepository.save(trainingType);
        sampleTrainer.setTrainingType(trainingType);
    }

    @Test
    void saveTrainer() {
        Trainer trainerToSave = getSampleTrainerForSave(false);

        trainerRepository.save(trainerToSave);

        trainerRepository.findById(trainerToSave.getId()).ifPresent(found -> assertEquals(trainerToSave, found));

    }

    @Test
    void updateTrainer() {
        Trainer trainerToUpdate = getSampleTrainerForSave(true);
        trainerToUpdate.setFirstName("new firstName");

        trainerRepository.save(trainerToUpdate);
        trainerRepository.findById(trainerToUpdate.getId()).map(Trainer::getFirstName).ifPresent(foundName -> assertEquals(trainerToUpdate.getFirstName(), foundName));
    }

    @Test
    void existsByUsername() {
        Trainer sampleTrainer = getSampleTrainerForSave(true);

        assertTrue(trainerRepository.existsByUserName(sampleTrainer.getUserName()));
        assertFalse(trainerRepository.existsByUserName(sampleTrainer.getUserName() + "1"));

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

        trainerRepository.findById(trainerToGet.getId()).ifPresent(found -> assertEquals(trainerToGet, found));
    }

    @Test
    void deleteByIdTrainer() {
        Trainer trainerToDelete = getSampleTrainerForSave(true);

        trainerRepository.deleteById(trainerToDelete.getId());

        assertTrue(trainerRepository.findById(trainerToDelete.getId()).isEmpty());
    }

    @Test
    void existsByUserNameTrainer() {
        Trainer sampleTrainer = getSampleTrainerForSave(true);

        assertTrue(trainerRepository.existsByUserName(sampleTrainer.getUserName()));
        assertFalse(trainerRepository.existsByUserName(sampleTrainer.getUserName() + "1"));
    }

    @Test
    void findByUserNameTrainer() {
        Trainer sampleTrainer = getSampleTrainerForSave(true);

        Optional<Trainer> foundTrainer = trainerRepository.findByUserName(sampleTrainer.getUserName());
        assertNotNull(foundTrainer);
        assertEquals(sampleTrainer, foundTrainer.get());
    }

    @Test
    void removeByUserNameTrainer() {
        Trainer trainerToRemove = getSampleTrainerForSave(true);

        trainerRepository.removeByUserName(trainerToRemove.getUserName());
        entityManager.flush();
        entityManager.clear();
        assertTrue(trainerRepository.findById(trainerToRemove.getId()).isEmpty());
    }

    @Test
    void findByTraineesEmptyAndIsActiveTrue() {
        Trainer activeTrainerWithoutTrainees = getSampleTrainerForSave(true);

        Trainer activeTrainerWithTrainees = getSampleTrainerForSave(true);
        trainerService.addTraineeToTrainer(traineeRepository.save(createSampleTrainee(false)).getId(), activeTrainerWithTrainees.getId());  // добавляем тренируемого

        Trainer inactiveTrainerWithoutTrainees = getSampleTrainerForSave(true);
        inactiveTrainerWithoutTrainees.setIsActive(false);

        Trainer inactiveTrainerWithTrainees = getSampleTrainerForSave(true);
        trainerService.addTraineeToTrainer(traineeRepository.save(createSampleTrainee(false)).getId(), inactiveTrainerWithTrainees.getId());  // добавляем тренируемого
        inactiveTrainerWithTrainees.setIsActive(false);

        trainerRepository.saveAll(List.of(
            inactiveTrainerWithoutTrainees,
            inactiveTrainerWithTrainees
        ));

        List<Trainer> result = trainerRepository.findByTraineesNullAndIsActiveTrue();

        assertEquals(1, result.size());
        assertEquals(activeTrainerWithoutTrainees, result.get(0));
    }

    @Test
    void testCountByUserNameIsStartingWith() {
        String prefix = "sample";
        int count = 5;

        List<Trainer> sampleTrainers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Trainer trainer = getSampleTrainerForSave(false);
            trainer.setUserName(prefix + i);
            sampleTrainers.add(trainer);
        }

        trainerRepository.saveAll(sampleTrainers);

        long result = trainerRepository.countByUserNameStartsWith(prefix);
        assertEquals(count, result);
    }

    @Test
    void testExistsByUserNameAndPassword() {

        Trainer sampleTrainer = getSampleTrainerForSave(true);

        assertTrue(trainerRepository.existsByUserNameAndPassword(sampleTrainer.getUserName(), sampleTrainer.getPassword()));
        assertFalse(trainerRepository.existsByUserNameAndPassword(sampleTrainer.getUserName(), "incorrectPassword"));
    }

    @Test
    void testExistsByFirstNameAndLastNameAndIsActiveTrue() {
        Trainer sampleTrainer = getSampleTrainerForSave(true);

        assertTrue(trainerRepository.existsByFirstNameAndLastNameAndIsActiveTrue(sampleTrainer.getFirstName(), sampleTrainer.getLastName()));

        assertFalse(trainerRepository.existsByFirstNameAndLastNameAndIsActiveTrue("IncorrectFirstName", "IncorrectLastName"));
    }

}

