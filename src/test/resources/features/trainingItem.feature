Feature: training items actions

  Scenario: create training item + get trainer workload + remove trainee + get trainer workload
    Given trainer with params
      | firstName        | trainerFirstName1 |
      | lastName         | trainerLastName1  |
      | trainingTypeName | CARDIO            |
    And trainee with params
      | firstName | traineeFirstName1 |
      | lastName  | traineeLastName1  |
    And trainer logged in
    When client calls \training\add with params
      | traineeUserName  | traineeFirstName1.traineeLastName1 |
      | trainerUserName  | trainerFirstName1.trainerLastName1 |
      | trainingName     | first training                     |
      | trainingDate     | 2024-11-11                         |
      | trainingDuration | 1                                  |
    Then trainer workload from second microservice should be created
    When trainer calls delete \trainee with username "traineeFirstName1.traineeLastName1"
    And trainer logged in
    Then training item from second microservice should be removed

  Scenario: create training item + get trainer workload + remove trainee from trainer + get trainer workload
    Given trainer with params
      | firstName        | trainerFirstName1 |
      | lastName         | trainerLastName1  |
      | trainingTypeName | CARDIO            |
    And trainee with params
      | firstName | traineeFirstName1 |
      | lastName  | traineeLastName1  |
    And trainee logged in
    When client calls \training\add with params
      | traineeUserName  | traineeFirstName1.traineeLastName1 |
      | trainerUserName  | trainerFirstName1.trainerLastName1 |
      | trainingName     | first training                     |
      | trainingDate     | 2024-11-11                         |
      | trainingDuration | 1                                  |
    Then trainer workload from second microservice should be created
    When trainee calls put \trainee\trainers with params
      | userName         | traineeFirstName1.traineeLastName1 |
      | trainerUserNames |                                    |
    Then training item from second microservice should be removed

