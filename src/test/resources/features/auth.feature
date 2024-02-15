Feature: user auth actions

  Scenario: trainer registration
    When trainer calls \register with params
      | firstName        | trainerFirstName |
      | lastName         | trainerLastName  |
      | trainingTypeName | CARDIO           |
    Then trainer response body should contain username "trainerFirstName.trainerLastName" and correct password of user

  Scenario: trainer login
    Given trainer with params
      | firstName        | trainerFirstName1 |
      | lastName         | trainerLastName1  |
      | trainingTypeName | CARDIO            |
    When trainer calls \login with username "trainerFirstName1.trainerLastName1" and generated password
    Then trainer response body should contain bearer code

  Scenario: trainee registration
    When trainee calls \register with params
      | firstName | traineeFirstName |
      | lastName  | traineeLastName  |
    Then trainee response body should contain username "traineeFirstName.traineeLastName" and correct password of user

  Scenario: trainee login
    Given trainee with params
      | firstName | traineeFirstName1 |
      | lastName  | traineeLastName1  |
    When trainee calls \login with username "traineeFirstName1.traineeLastName1" and generated password
    Then trainee response body should contain bearer code
