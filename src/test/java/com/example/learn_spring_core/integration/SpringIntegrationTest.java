package com.example.learn_spring_core.integration;

import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.security.JwtService;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.learn_spring_core.integration.UserAuthStepDefinitions.loginResponse;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableFeignClients
@EnableRabbit
@ActiveProfiles("test")
public class SpringIntegrationTest {

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected TraineeRepository traineeRepository;

    @Autowired
    protected TrainerRepository trainerRepository;

    @Autowired
    private MainFeignClient mainFeignClient;

    public ResponseEntity<UserCredentialsDTO> postRegisterTrainer(Map<String, String> map) {
        return mainFeignClient.registerTrainer(
            map.get("firstName"),
            map.get("lastName"),
            TrainingTypeName.valueOf(map.get("trainingTypeName"))
        );

    }

    public ResponseEntity<UserCredentialsDTO> postRegisterTrainee(Map<String, String> map) {
        return mainFeignClient.registerTrainee(
            map.get("firstName"),
            map.get("lastName"),
            Optional.ofNullable(map.get("dateOfBirth")).map(LocalDate::parse).orElse(null),
            map.get("address")
        );
    }

    public ResponseEntity<String> getLoginTrainer(String userName, String password) {
        return mainFeignClient.loginTrainer(userName, password);
    }

    public ResponseEntity<String> getLoginTrainee(String userName, String password) {
        return mainFeignClient.loginTrainee(userName, password);
    }

    public void postAddTraining(Map<String, String> map) {
        mainFeignClient.addTraining(
            "Bearer " + loginResponse.getBody(),
            map.get("traineeUserName"),
            map.get("trainerUserName"),
            map.get("trainingName"),
            LocalDate.parse(map.get("trainingDate")),
            Long.valueOf(map.get("trainingDuration"))
        );

    }

    public TrainerWorkloadDTO getTrainerWorkload(String userName) {
        return mainFeignClient.getTrainerWorkload("Bearer " + loginResponse.getBody(), userName);
    }

    public void deleteTrainee(String userName) {
        mainFeignClient.deleteTrainee("Bearer " + loginResponse.getBody(), userName);
    }

    public ResponseEntity<List<TrainerOfTraineeDTO>> updateTraineeTrainersList(Map<String, String> map) {
        return mainFeignClient.updateTraineeTrainersList(
            "Bearer " + loginResponse.getBody(),
            map.get("userName"),
            Optional.ofNullable(map.get("trainerUserNames")).map(names-> Arrays.stream(names.split(",")).toList()).orElse(List.of())
        );
    }


}