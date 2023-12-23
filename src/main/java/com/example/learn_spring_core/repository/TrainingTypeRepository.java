package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.repository.entity.TrainingType;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TrainingTypeRepository extends BaseRepository<TrainingType> {

    public static final String INSERT_SQL = "INSERT INTO training_type (training_type_name) VALUES (?)";
    public static final String UPDATE_SQL = "UPDATE training_type SET training_type_name=? WHERE id=?";

    @Override
    public Class<TrainingType> getEntityClass() {
        return TrainingType.class;
    }

    @Override
    public TrainingType save(TrainingType trainingType) {
        KeyHolder keyHolder = keyHolderGenerator.getNewGeneratedKeyHoler();

        jdbcTemplate.update(INSERT_SQL, trainingType.getTrainingTypeName());
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, trainingType.getTrainingTypeName());

                return ps;
            }, keyHolder
        );
        trainingType.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return trainingType;
    }

    @Override
    public void update(TrainingType trainingType) {
        jdbcTemplate.update(UPDATE_SQL, trainingType.getTrainingTypeName(), trainingType.getId());
    }

}
