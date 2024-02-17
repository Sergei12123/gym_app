package com.example.learn_spring_core.integration;

import com.example.learn_spring_core.dto.UserCredentialsDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class UserAuthStepDefinitions extends SpringIntegrationTest {

    public static ResponseEntity<UserCredentialsDTO> traineeRegisterResponse;

    public static ResponseEntity<UserCredentialsDTO> trainerRegisterResponse;

    public static ResponseEntity<String> loginResponse;

    @After
    public void tearDown() {
        traineeRepository.deleteAllInBatch();
        trainerRepository.deleteAllInBatch();
    }

    @When("{role} calls \\register with params")
    public void userCallsRegisterWithParams(String role, DataTable dataTable) {
        if (role.equals("trainee")) {
            traineeRegisterResponse = postRegisterTrainee(dataTable.asMap(String.class, String.class));
        } else {
            trainerRegisterResponse = postRegisterTrainer(dataTable.asMap(String.class, String.class));
        }
    }

    @Then("{role} response body should contain username {string} and correct password of user")
    public void responseBodyShouldContainCorrectUsernameAndPasswordOfUser(String role, String expectedUsername) {
        UserCredentialsDTO userCredentialsDTO = role.equals("trainee") ? traineeRegisterResponse.getBody() : trainerRegisterResponse.getBody();
        assertNotNull(userCredentialsDTO);
        assertEquals(expectedUsername, userCredentialsDTO.getUsername());

        Assertions.assertEquals(10, userCredentialsDTO.getPassword().length(), "Password length should be 10 characters");
        for (char ch : userCredentialsDTO.getPassword().toCharArray()) {
            Assertions.assertTrue(Character.isLetterOrDigit(ch));
        }
    }

    @Given("{role} with params")
    public void userWithParams(String role, DataTable dataTable) {
        if (role.equals("trainee")) {
            traineeRegisterResponse = postRegisterTrainee(dataTable.asMap(String.class, String.class));
        } else {
            trainerRegisterResponse = postRegisterTrainer(dataTable.asMap(String.class, String.class));
        }
    }

    @When("{role} calls \\login with username {string} and generated password")
    public void traineeCallsLoginWithThatUsernameAndGeneratedPassword(String role, String trainerUserName) {
        if (role.equals("trainee")) {
            assertNotNull(traineeRegisterResponse.getBody());
            loginResponse = getLoginTrainee(trainerUserName, traineeRegisterResponse.getBody().getPassword());
        } else {
            assertNotNull(trainerRegisterResponse.getBody());
            loginResponse = getLoginTrainer(trainerUserName, trainerRegisterResponse.getBody().getPassword());
        }
    }

    @Then("{role} response body should contain bearer code")
    public void responseBodyShouldContainBearerCode(String role) {
        String bearerToken = loginResponse.getBody();
        assertNotNull(bearerToken);
        assertTrue(jwtService.checkBearer("Bearer " + bearerToken));
    }

    @And("{role} logged in")
    public void roleLoggedIn(String role) {
        if (role.equals("trainee")) {
            assertNotNull(traineeRegisterResponse.getBody());
            loginResponse = getLoginTrainee(traineeRegisterResponse.getBody().getUsername(), traineeRegisterResponse.getBody().getPassword());
        } else {
            assertNotNull(trainerRegisterResponse.getBody());
            loginResponse = getLoginTrainer(trainerRegisterResponse.getBody().getUsername(), trainerRegisterResponse.getBody().getPassword());
        }

    }

    @ParameterType("trainer|trainee")
    public String role(String role) {
        return role;
    }

}
