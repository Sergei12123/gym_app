CREATE TABLE IF NOT EXISTS trainee
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    date_of_birth   DATE,
    address         VARCHAR(255),
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    user_name       VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    is_active       TINYINT(1)   NOT NULL
);

CREATE TABLE IF NOT EXISTS training_type
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    training_type_name  VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS trainer
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    training_type_id    INT,
    first_name          VARCHAR(255) NOT NULL,
    last_name           VARCHAR(255) NOT NULL,
    user_name           VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    is_active           TINYINT(1)   NOT NULL,
    FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);

CREATE TABLE IF NOT EXISTS training (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    trainee_id          INT,
    trainer_id          INT,
    training_name       VARCHAR(255),
    training_type_id    INT,
    training_date       DATE,
    training_duration   BIGINT,
    FOREIGN KEY (trainee_id) REFERENCES trainee(id),
    FOREIGN KEY (trainer_id) REFERENCES trainer(id),
    FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);

