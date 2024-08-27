package com.example.gym_app.repository.impl;

import com.example.gym_app.entity.Training;
import com.example.gym_app.repository.TrainingRepository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TrainingRepositoryImpl extends BaseRepositoryImpl<Training> implements TrainingRepository {

    public static final String INSERT_SQL = "INSERT INTO training (trainee_id, trainer_id, training_name, training_type_id, training_date, training_duration) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SQL = "UPDATE training SET trainee_id=?, trainer_id=?, training_name=?, training_type_id=?, training_date=?, training_duration=? WHERE id=?";

    @Override
    public Class<Training> getEntityClass() {
        return Training.class;
    }

    @Override
    public Training save(Training training) {
        KeyHolder keyHolder = keyHolderGenerator.getNewGeneratedKeyHoler();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, training.getTraineeId());
                    ps.setLong(2, training.getTrainerId());
                    ps.setString(3, training.getTrainingName());
                    ps.setLong(4, training.getTrainingTypeId());
                    ps.setDate(5, Date.valueOf(training.getTrainingDate()));
                    ps.setLong(6, training.getTrainingDuration());
                    return ps;
                }, keyHolder
        );
        training.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return training;

    }

    @Override
    public void update(Training training) {
        jdbcTemplate.update(
                UPDATE_SQL,
                training.getTraineeId(),
                training.getTrainerId(),
                training.getTrainingName(),
                training.getTrainingTypeId(),
                training.getTrainingDate(),
                training.getTrainingDuration(),
                training.getId()
        );
    }

}
