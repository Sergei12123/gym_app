package com.example.gym_app.repository.impl;

import com.example.gym_app.entity.Trainer;
import com.example.gym_app.repository.TrainerRepository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TrainerRepositoryImpl extends UserRepositoryImpl<Trainer> implements TrainerRepository {

    public static final String INSERT_SQL = "INSERT INTO trainer (training_type_id, first_name, last_name, user_name, password, is_active) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SQL = "UPDATE trainer SET training_type_id=?, first_name=?, last_name=?, user_name=?, password=?, is_active=? WHERE id=?";

    @Override
    public Class<Trainer> getEntityClass() {
        return Trainer.class;
    }

    @Override
    public Trainer save(Trainer trainer) {
        KeyHolder keyHolder = keyHolderGenerator.getNewGeneratedKeyHoler();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, trainer.getTrainingTypeId());
                    ps.setString(2, trainer.getFirstName());
                    ps.setString(3, trainer.getLastName());
                    ps.setString(4, trainer.getUserName());
                    ps.setString(5, trainer.getPassword());
                    ps.setBoolean(6, trainer.getIsActive());
                    return ps;
                }, keyHolder
        );
        trainer.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return trainer;
    }

    @Override
    public void update(Trainer trainer) {
        jdbcTemplate.update(
                UPDATE_SQL,
                trainer.getTrainingTypeId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUserName(),
                trainer.getPassword(),
                Boolean.TRUE.equals(trainer.getIsActive()) ? 1 : 0,
                trainer.getId()
        );

    }
}