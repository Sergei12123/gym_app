package com.example.gym_app.repository;

import com.example.gym_app.entity.Trainee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.gym_app.utils.SampleCreator.createSampleTrainee;
import static com.example.gym_app.utils.SampleCreator.createSampleTrainees;
import static org.junit.jupiter.api.Assertions.*;

class TraineeRepositoryTest extends BaseRepositoryTest {

    @Test
    void saveTrainee() {
        Trainee traineeToSave = createSampleTrainee(false);
        traineeRepository.save(traineeToSave);

        traineeRepository.findById(traineeToSave.getId()).ifPresent(found -> assertEquals(traineeToSave, found));
    }

    @Test
    void updateTrainee() {
        Trainee traineeToUpdate = createSampleTrainee(false);
        traineeRepository.save(traineeToUpdate);
        traineeToUpdate.setAddress("new address");

        traineeRepository.save(traineeToUpdate);
        traineeRepository.findById(traineeToUpdate.getId()).map(Trainee::getAddress).ifPresent(foundAddress -> assertEquals(traineeToUpdate.getAddress(), foundAddress));
    }

    @Test
    void existsByUsername() {
        Trainee sampleTrainee = createSampleTrainee(false);

        traineeRepository.save(sampleTrainee);
        assertTrue(traineeRepository.existsByUserName(sampleTrainee.getUserName()));
        assertFalse(traineeRepository.existsByUserName(sampleTrainee.getUserName() + "1"));

    }

    @Test
    void findAllTrainee() {
        List<Trainee> sampleTrainees = createSampleTrainees(false, 3);
        traineeRepository.saveAll(sampleTrainees);

        List<Trainee> result = traineeRepository.findAll();

        assertEquals(sampleTrainees.size(), result.size());
        assertTrue(sampleTrainees.containsAll(result) && result.containsAll(sampleTrainees));
    }

    @Test
    void getByIdTrainee() {
        Trainee traineeToGet = createSampleTrainee(false);
        traineeRepository.save(traineeToGet);

        traineeRepository.findById(traineeToGet.getId()).ifPresent(found -> assertEquals(traineeToGet, found));
    }

    @Test
    void deleteByIdTrainee() {
        Trainee traineeToDelete = createSampleTrainee(false);
        traineeRepository.save(traineeToDelete);

        traineeRepository.deleteById(traineeToDelete.getId());
        assertTrue(traineeRepository.findById(traineeToDelete.getId()).isEmpty());
    }

    @Test
    void existsByUserNameTrainee() {
        Trainee sampleTrainee = createSampleTrainee(false);
        traineeRepository.save(sampleTrainee);

        assertTrue(traineeRepository.existsByUserName(sampleTrainee.getUserName()));
        assertFalse(traineeRepository.existsByUserName(sampleTrainee.getUserName() + "1"));
    }

    @Test
    void findByUserNameTrainee() {
        Trainee sampleTrainee = createSampleTrainee(false);
        traineeRepository.save(sampleTrainee);

        Trainee foundTrainee = traineeRepository.findByUserName(sampleTrainee.getUserName());
        assertNotNull(foundTrainee);
        assertEquals(sampleTrainee, foundTrainee);
    }

    @Test
    void removeByUserNameTrainee() {
        Trainee traineeToRemove = createSampleTrainee(false);
        traineeRepository.save(traineeToRemove);

        traineeRepository.removeByUserName(traineeToRemove.getUserName());
        assertFalse(traineeRepository.existsByUserName(traineeToRemove.getUserName()));
    }

}

