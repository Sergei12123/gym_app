package com.example.learn_spring_core.service;

import com.example.learn_spring_core.TestsParent;
import com.example.learn_spring_core.messaging.service.TrainingItemService;
import com.example.learn_spring_core.entity.Trainee;
import com.example.learn_spring_core.entity.Trainer;
import com.example.learn_spring_core.exception.IncorrectCredentialsException;
import com.example.learn_spring_core.exception.UserNotAllowedToLoginException;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.repository.UserRepository;
import com.example.learn_spring_core.security.BruteForceProtectionService;
import com.example.learn_spring_core.security.JwtService;
import com.example.learn_spring_core.service.impl.TraineeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainee;
import static com.example.learn_spring_core.utils.SampleCreator.createSampleTrainer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TraineeServiceTest extends TestsParent {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingService trainingService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BruteForceProtectionService bruteForceProtectionService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field hack = TraineeServiceImpl.class.getSuperclass().getSuperclass().getDeclaredField("currentRepository");
        hack.setAccessible(true);
        hack.set(traineeService, traineeRepository);
        Field hack2 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("passwordEncoder");
        hack2.setAccessible(true);
        hack2.set(traineeService, passwordEncoder);
        Field hack3 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("bruteForceProtectionService");
        hack3.setAccessible(true);
        hack3.set(traineeService, bruteForceProtectionService);
        Field hack4 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("authenticationManager");
        hack4.setAccessible(true);
        hack4.set(traineeService, authenticationManager);
        Field hack5 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("jwtService");
        hack5.setAccessible(true);
        hack5.set(traineeService, jwtService);
        Field hack6 = TraineeServiceImpl.class.getSuperclass().getDeclaredField("trainingService");
        hack6.setAccessible(true);
        hack6.set(traineeService, trainingService);
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(invocation -> {
            Trainee traineeArgument = invocation.getArgument(0);
            traineeArgument.setId(1L);
            return traineeArgument;
        });
        when(trainerRepository.save(any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainerArgument = invocation.getArgument(0);
            trainerArgument.setId(1L);
            return trainerArgument;
        });

    }

    @Test
    void testLogin() {
        String userName = "sampleUser";
        String password = "samplePassword";
        Authentication authentication = new UsernamePasswordAuthenticationToken(createSampleTrainee(true), password);
        String expectedResult = "generatedToken";
        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(true);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(expectedResult);
        String result = traineeService.login(userName, password);
        Assertions.assertEquals(expectedResult, result);

        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(true);
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);
        assertThrows(IncorrectCredentialsException.class, () -> traineeService.login(userName, password));

        when(bruteForceProtectionService.isAllowedToLogin(userName)).thenReturn(false);
        when(bruteForceProtectionService.getTillForUsername(userName)).thenReturn(LocalDateTime.now().plusMinutes(15).toString());
        assertThrows(UserNotAllowedToLoginException.class, () -> traineeService.login(userName, password));
    }

    @Test
    void testCreateTrainee() {

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        trainee.setAddress("123 Main St");

        when(traineeRepository.existsByUserName(Mockito.anyString())).thenReturn(false);
        when(passwordEncoder.encode(trainee.getPassword())).thenReturn("encodedPassword");
        traineeService.create(trainee);
        Mockito.verify(traineeRepository, times(1)).save(Mockito.any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        Long traineeIdToDelete = 1L;
        doNothing().when(traineeRepository).deleteById(traineeIdToDelete);

        traineeService.delete(traineeIdToDelete);

        Mockito.verify(traineeRepository, times(1)).deleteById(traineeIdToDelete);
    }

    @Test
    void testFindAllTrainees() {
        Trainee trainee1 = createSampleTrainee(true);

        Trainee trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setFirstName("Alice");
        trainee2.setLastName("Smith");
        trainee2.setDateOfBirth(LocalDate.of(1985, 8, 22));
        trainee2.setAddress("456 Oak St");
        trainee2.setUserName("alice.smith");
        trainee2.setPassword("secure-pass");
        trainee2.setIsActive(true);

        List<Trainee> trainees = Arrays.asList(trainee1, trainee2);
        when(traineeRepository.findAll()).thenReturn(trainees);

        List<Trainee> allTrainees = traineeService.findAll();

        assertEquals(trainees, allTrainees);
        verify(traineeRepository, times(1)).findAll();
    }

    @Test
    void testGetTraineeById() {
        Long traineeId = 1L;
        Trainee trainee = createSampleTrainee(true);

        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));

        Trainee retrievedTrainee = traineeService.getById(traineeId);

        assertEquals(trainee, retrievedTrainee);
        verify(traineeRepository, times(1)).findById(traineeId);
    }

    @Test
    void testUpdateTrainee() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        when(traineeRepository.existsByUserName(sampleTrainee.getUserName())).thenReturn(true);

        traineeService.update(sampleTrainee);

        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testGenerateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUserName = "John.Doe";
        String expectedUserName1 = "John.Doe1";

        when(traineeRepository.countByUserNameStartsWith(expectedUserName)).thenReturn(0L);
        String result = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName, result);
        verify(traineeRepository, times(1)).countByUserNameStartsWith(expectedUserName);


        when(traineeRepository.countByUserNameStartsWith(expectedUserName)).thenReturn(1L);
        String result2 = traineeService.generateUsername(firstName, lastName);
        Assertions.assertEquals(expectedUserName1, result2);
        verify(((UserRepository<?>) traineeRepository), times(2)).countByUserNameStartsWith(expectedUserName);

    }

    @Test
    void testGenerateRandomPassword() {
        for (int i = 0; i < 5; i++) {
            String result = traineeService.generateRandomPassword();
            Assertions.assertEquals(10, result.length(), "Password length should be 10 characters");
            for (char ch : result.toCharArray()) {
                Assertions.assertTrue(Character.isLetterOrDigit(ch));
            }
        }
    }

    @Test
    void testChangePassword() {
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findByUserName(sampleTrainee.getUserName())).thenReturn(Optional.of(sampleTrainee));
        when(passwordEncoder.matches(sampleTrainee.getPassword(), sampleTrainee.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        traineeService.changePassword(sampleTrainee.getUserName(), sampleTrainee.getPassword(), "newPassword");
        Assertions.assertEquals("newEncodedPassword", sampleTrainee.getPassword());

        Trainee sampleTraineeNotFound = createSampleTrainee(true);
        when(traineeRepository.findByUserName(sampleTrainee.getUserName())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> traineeService.changePassword(sampleTraineeNotFound.getUserName(), sampleTraineeNotFound.getPassword(), "newPassword"));

        Trainee sampleTraineeIncorrectPass = createSampleTrainee(true);
        when(traineeRepository.findByUserName(sampleTraineeIncorrectPass.getUserName())).thenReturn(Optional.of(sampleTraineeIncorrectPass));
        when(passwordEncoder.matches(sampleTraineeIncorrectPass.getPassword(), sampleTraineeIncorrectPass.getPassword())).thenReturn(false);

        assertThrows(IncorrectCredentialsException.class, () -> traineeService.changePassword(sampleTraineeIncorrectPass.getUserName(), sampleTraineeIncorrectPass.getPassword(), "newPassword"));

    }

    @Test
    void testActivate() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        sampleTrainee.setIsActive(false);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        traineeService.activate(traineeId);
        Assertions.assertTrue(sampleTrainee.getIsActive());
    }

    @Test
    void testDeactivate() {
        Long traineeId = 1L;
        Trainee sampleTrainee = createSampleTrainee(true);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(sampleTrainee));
        traineeService.deactivate(traineeId);
        Assertions.assertFalse(sampleTrainee.getIsActive());
    }

    @Test
    void testAddTrainerToTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = createSampleTrainer(true);

        when(trainerRepository.findByUserName(trainer.getUserName())).thenReturn(Optional.of(trainer));
        when(traineeRepository.save(trainee)).thenAnswer(invocationOnMock -> {
            Trainee trainee1 = invocationOnMock.getArgument(0);
            trainee1.getTrainers().add(trainer);
            return trainee1;
        });

        traineeService.addTrainerToTrainee(trainer.getUserName(), trainee);

        Assertions.assertTrue(trainee.getTrainers().contains(trainer));
    }

    @Test
    void testRemoveTrainerFromTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        Trainer trainer = new Trainer();
        trainer.setId(2L);

        trainee.getTrainers().add(trainer);

        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findById(2L)).thenReturn(Optional.of(trainer));

        traineeService.removeTrainerFromTrainee(1L, 2L);

        Assertions.assertTrue(trainee.getTrainers().isEmpty());
    }

    @Test
    void testExistByFirstNameAndLastNameActiveUser() {
        String firstName = "John";
        String lastName = "Doe";
        boolean expectedResult = true;

        when(traineeRepository.existsByFirstNameAndLastNameAndIsActiveTrue(firstName, lastName)).thenReturn(expectedResult);

        boolean result = traineeService.existByFirstNameAndLastNameActiveUser(firstName, lastName);

        Assertions.assertEquals(expectedResult, result);
    }

}
