package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainee;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainees;
import static org.junit.jupiter.api.Assertions.*;

class TraineeRepositoryTest extends BaseRepositoryTest {

    @Test
    void saveTrainee() {
        Trainee traineeToSave = createSampleTrainee(false);
        traineeRepository.save(traineeToSave);
        Trainee foundTrainee = traineeRepository.getById(traineeToSave.getId());
        assertEquals(traineeToSave, foundTrainee);
    }

    @Test
    void updateTrainee() {
        Trainee traineeToUpdate = createSampleTrainee(false);
        traineeRepository.save(traineeToUpdate);
        traineeToUpdate.setAddress("new address");

        traineeRepository.update(traineeToUpdate);
        assertEquals(traineeToUpdate.getAddress(), traineeRepository.getById(traineeToUpdate.getId()).getAddress());
    }

    @Test
    void existsByUsername() {
        Trainee sampleTrainee = createSampleTrainee(false);

        traineeRepository.save(sampleTrainee);
        assertTrue(traineeRepository.existsByUsername(sampleTrainee.getUserName()));
        assertFalse(traineeRepository.existsByUsername(sampleTrainee.getUserName() + "1"));

    }

    @Test
    void findAllTrainee() {
        List<Trainee> sampleTrainees = createSampleTrainees(false, 3);
        for (Trainee trainee : sampleTrainees) {
            traineeRepository.save(trainee);
        }

        List<Trainee> result = traineeRepository.findAll();

        assertEquals(sampleTrainees.size(), result.size());
        assertTrue(sampleTrainees.containsAll(result) && result.containsAll(sampleTrainees));
    }

    @Test
    void getByIdTrainee() {
        Trainee traineeToGet = createSampleTrainee(false);
        traineeRepository.save(traineeToGet);
        Trainee foundTrainee = traineeRepository.getById(traineeToGet.getId());
        assertEquals(traineeToGet, foundTrainee);
    }

    @Test
    void deleteByIdTrainee() {
        Trainee traineeToDelete = createSampleTrainee(false);
        traineeRepository.save(traineeToDelete);

        traineeRepository.deleteById(traineeToDelete.getId());
        assertNull(traineeRepository.getById(traineeToDelete.getId()));
    }

}

