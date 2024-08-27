package com.example.gym_app.service;

import com.example.gym_app.TestsParent;
import com.example.gym_app.client.service.TrainingItemService;
import com.example.gym_app.entity.Trainee;
import com.example.gym_app.entity.Trainer;
import com.example.gym_app.entity.Training;
import com.example.gym_app.entity.TrainingType;
import com.example.gym_app.repository.TraineeRepository;
import com.example.gym_app.repository.TrainerRepository;
import com.example.gym_app.repository.TrainingRepository;
import com.example.gym_app.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.gym_app.utils.SampleCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingServiceTest extends TestsParent {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingItemService trainingItemService;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field hack = TrainingServiceImpl.class.getSuperclass().getDeclaredField("currentRepository");
        hack.setAccessible(true);
        hack.set(trainingService, trainingRepository);

        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
            Training trainingArgument = invocation.getArgument(0);
            trainingArgument.setId(1L);
            return trainingArgument;
        });
    }

    @Test
    void testCreateTraining() {
        Training training = createSampleTraining(false);
        when(traineeRepository.findByUserName(training.getTrainee().getUserName())).thenReturn(Optional.of(training.getTrainee()));
        when(trainerRepository.findByUserName(training.getTrainer().getUserName())).thenReturn(Optional.of(training.getTrainer()));
        trainingService.create(training);

        Mockito.verify(trainingRepository, times(1)).save(Mockito.any(Training.class));
    }

    @Test
    void testFindAllTrainings() {
        List<Training> trainings = createSampleTrainings(true, 3);
        when(trainingRepository.findAll()).thenReturn(trainings);

        List<Training> allTrainings = trainingService.findAll();

        assertEquals(trainings, allTrainings);
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void testGetTrainingById() {
        Long trainingId = 1L;
        Training training = createSampleTraining(true);
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));

        Training retrievedTraining = trainingService.getById(trainingId);

        assertEquals(training, retrievedTraining);
        verify(trainingRepository, times(1)).findById(trainingId);
    }

    @Test
    void testGetAllByTraineeUserNameAndTrainingName() {
        String traineeUserName = "sampleTrainee";
        String trainingName = "sampleTraining";

        List<Training> expectedTrainings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expectedTrainings.add(createSampleTraining(false));
        }
        when(trainingRepository.findByTrainee_UserNameAndTrainingName(traineeUserName, trainingName)).thenReturn(expectedTrainings);

        List<Training> result = trainingService.getAllByTraineeUserNameAndTrainingName(traineeUserName, trainingName);

        assertEquals(expectedTrainings, result);
    }

    @Test
    void testGetAllByTrainerUserNameAndTrainingName() {
        String trainerUserName = "sampleTrainer";
        String trainingName = "sampleTraining";

        List<Training> expectedTrainings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            expectedTrainings.add(createSampleTraining(false));
        }
        when(trainingRepository.findByTrainer_UserNameAndTrainingName(trainerUserName, trainingName)).thenReturn(expectedTrainings);

        List<Training> result = trainingService.getAllByTrainerUserNameAndTrainingName(trainerUserName, trainingName);

        assertEquals(expectedTrainings, result);
    }


    @Test
    void testGetTraineeTrainingList() {
        Trainee sampleTrainee = createSampleTrainee(false);
        Trainer sampleTrainer = createSampleTrainer(false);
        LocalDate dateFrom = LocalDate.now().minusDays(7);
        LocalDate dateTo = LocalDate.now().plusDays(7);
        TrainingType trainingType = createSampleTrainingType(false);

        List<Training> fullTrainingList = createSampleTrainings(false, 10);
        for (int i = 0; i < 5; i++) {
            fullTrainingList.get(i).setTrainingType(trainingType);
            fullTrainingList.get(i).setTrainer(sampleTrainer);
            fullTrainingList.get(i).setTrainee(sampleTrainee);
            fullTrainingList.get(i).setTrainingDate(LocalDate.now());
        }
        when(trainingRepository.findByTrainee_UserName(sampleTrainee.getUserName())).thenReturn(fullTrainingList);
        when(traineeRepository.existsByUserName(sampleTrainee.getUserName())).thenReturn(true);
        List<Training> result = trainingService.getTraineeTrainingList(sampleTrainee.getUserName(), dateFrom, dateTo, sampleTrainer.getUserName(), trainingType);

        assertEquals(5, result.size());
        assertEquals(filterByTrainingFields(fullTrainingList, dateFrom, dateTo, sampleTrainee.getUserName(), sampleTrainer.getUserName(), trainingType), result);
    }

    @Test
    void testGetTrainerTrainingList() {
        Trainee sampleTrainee = createSampleTrainee(false);
        Trainer sampleTrainer = createSampleTrainer(false);
        LocalDate dateFrom = LocalDate.now().minusDays(7);
        LocalDate dateTo = LocalDate.now().plusDays(7);

        List<Training> fullTrainingList = createSampleTrainings(false, 10);
        for (int i = 0; i < 5; i++) {
            fullTrainingList.get(i).setTrainer(sampleTrainer);
            fullTrainingList.get(i).setTrainee(sampleTrainee);
            fullTrainingList.get(i).setTrainingDate(LocalDate.now());
        }
        when(trainingRepository.findByTrainer_UserName(sampleTrainer.getUserName())).thenReturn(fullTrainingList);
        when(trainerRepository.existsByUserName(sampleTrainer.getUserName())).thenReturn(true);
        List<Training> result = trainingService.getTrainerTrainingList(sampleTrainer.getUserName(), dateFrom, dateTo, sampleTrainee.getUserName());

        assertEquals(5, result.size());
        assertEquals(filterByTrainingFields(fullTrainingList, dateFrom, dateTo, sampleTrainee.getUserName(), sampleTrainer.getUserName(), null), result);
    }

    private List<Training> filterByTrainingFields(List<Training> trainingFullList, LocalDate dateFrom, LocalDate dateTo, String traineeUserName, String trainerUserName, TrainingType trainingType) {
        return trainingFullList.stream()
                .filter(training -> dateFrom == null || dateTo == null || training.getTrainingDate().isAfter(dateFrom) && training.getTrainingDate().isBefore(dateTo))
                .filter(training -> traineeUserName == null || training.getTrainee().getUserName().equals(traineeUserName))
                .filter(training -> trainerUserName == null || training.getTrainer().getUserName().equals(trainerUserName))
                .filter(training -> trainingType == null || training.getTrainingType().equals(trainingType))
                .toList();
    }


}
