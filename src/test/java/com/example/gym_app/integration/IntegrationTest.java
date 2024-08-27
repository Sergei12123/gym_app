package com.example.gym_app.integration;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/", glue = "com.example.gym_app.integration")
public class IntegrationTest {

    @Test
    public void testFunctionUnderTest_testCase1() {
        assertEquals(1, 1);
    }
}
