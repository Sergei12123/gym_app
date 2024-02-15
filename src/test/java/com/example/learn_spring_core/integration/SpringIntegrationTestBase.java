package com.example.learn_spring_core.integration;

import com.example.learn_spring_core.configuration.RabbitMQConfig;
import com.example.learn_spring_core.dto.TrainerOfTraineeDTO;
import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import com.example.learn_spring_core.entity.enums.TrainingTypeName;
import com.example.learn_spring_core.repository.TraineeRepository;
import com.example.learn_spring_core.repository.TrainerRepository;
import com.example.learn_spring_core.security.BearerRabbitListener;
import com.example.learn_spring_core.security.JwtService;
import feign.Feign;
import io.cucumber.datatable.DataTable;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

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
public class SpringIntegrationTestBase {

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected TraineeRepository traineeRepository;

    @Autowired
    protected TrainerRepository trainerRepository;

    @Autowired
    private MainFeignClient mainFeignClient;

    @Autowired
    private BearerRabbitListener bearerRabbitListener;

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