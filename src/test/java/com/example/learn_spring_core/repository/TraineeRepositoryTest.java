package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.Trainee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainee;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainees;
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

        Trainee foundTrainee = traineeRepository.findByUserName(sampleTrainee.getUserName()).get();
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

    @Test
    void testCountByUserNameIsStartingWith() {
        String prefix = "sample";
        int count = 5;

        List<Trainee> sampleTrainees = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Trainee trainee = createSampleTrainee(false);
            trainee.setUserName(prefix + i);
            sampleTrainees.add(trainee);
        }

        traineeRepository.saveAll(sampleTrainees);

        long result = traineeRepository.countByUserNameStartsWith(prefix);
        assertEquals(count, result);
    }

    @Test
    void testExistsByUserNameAndPassword() {
        String userName = "sampleUser";
        String password = "samplePassword";

        Trainee sampleTrainee = createSampleTrainee(false);
        sampleTrainee.setUserName(userName);
        sampleTrainee.setPassword(password);

        traineeRepository.save(sampleTrainee);

        assertTrue(traineeRepository.existsByUserNameAndPassword(userName, password));
        assertFalse(traineeRepository.existsByUserNameAndPassword(userName, "incorrectPassword"));
    }

    @Test
    void testExistsByFirstNameAndLastNameAndIsActiveTrue() {
        String firstName = "John";
        String lastName = "Doe";

        Trainee sampleTrainee = createSampleTrainee(false);
        sampleTrainee.setFirstName(firstName);
        sampleTrainee.setLastName(lastName);

        traineeRepository.save(sampleTrainee);

        assertTrue(traineeRepository.existsByFirstNameAndLastNameAndIsActiveTrue(firstName, lastName));

        assertFalse(traineeRepository.existsByFirstNameAndLastNameAndIsActiveTrue("IncorrectFirstName", "IncorrectLastName"));
    }


}

