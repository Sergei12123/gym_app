package com.example.learn_spring_core.integration;

import com.example.learn_spring_core.dto.TrainerWorkloadDTO;
import com.example.learn_spring_core.dto.UserCredentialsDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.example.learn_spring_core.integration.UserAuthStepDefinitions.trainerRegisterResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainingItemStepDefinitions extends SpringIntegrationTest {

    public static Long workload;

    @When("client calls \\training\\add with params")
    public void clientCallsTrainingAddWithParams(DataTable dataTable) {
        postAddTraining(dataTable.asMap(String.class, String.class));
    }


    @Then("trainer workload from second microservice should be created")
    public void trainerWorkloadFromSecondMicroserviceShouldBeCreated() {
        assertNotNull(trainerRegisterResponse.getBody());
        UserCredentialsDTO trainerCredentials = trainerRegisterResponse.getBody();
        TrainerWorkloadDTO trainerWorkload = getTrainerWorkload(trainerCredentials.getUsername());
        assertNotNull(trainerWorkload);
        assertEquals(trainerCredentials.getUsername(), trainerWorkload.getTrainerUserName());
        workload = trainerWorkload.getWorkload().get(2024).get(11);
    }


    @When("trainer calls delete \\trainee with username {string}")
    public void trainerCallsDeleteTraineeWithParams(String username) {
        deleteTrainee(username);
    }

    @Then("training item from second microservice should be removed")
    public void trainerWorkloadFromSecondMicroserviceShouldBeRemoved() {
        assertNotNull(trainerRegisterResponse.getBody());
        UserCredentialsDTO trainerCredentials = trainerRegisterResponse.getBody();
        TrainerWorkloadDTO trainerWorkload = getTrainerWorkload(trainerCredentials.getUsername());
        assertNotNull(trainerWorkload);
        assertEquals(trainerCredentials.getUsername(), trainerWorkload.getTrainerUserName());
        assertEquals(workload - 1L, trainerWorkload.getWorkload().get(2024).get(11));
    }

    @When("trainee calls put \\trainee\\trainers with params")
    public void traineeCallsPutTraineeTrainersWithParams(DataTable dataTable) {
        updateTraineeTrainersList(dataTable.asMap(String.class, String.class));
    }
}
