package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.repository.entity.Trainee;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TraineeRepository extends UserRepository<Trainee> {

    public static final String INSERT_SQL = "INSERT INTO trainee (date_of_birth, address, first_name, last_name, user_name, password, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_SQL = "UPDATE trainee SET date_of_birth=?, address=?, first_name=?, last_name=?, user_name=?, password=?, is_active=? WHERE id=?";

    @Override
    public Class<Trainee> getEntityClass() {
        return Trainee.class;
    }

    @Override
    public Trainee save(Trainee trainee) {
        KeyHolder keyHolder = keyHolderGenerator.getNewGeneratedKeyHoler();
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1, Date.valueOf(trainee.getDateOfBirth()));
                ps.setString(2, trainee.getAddress());
                ps.setString(3, trainee.getFirstName());
                ps.setString(4, trainee.getLastName());
                ps.setString(5, trainee.getUserName());
                ps.setString(6, trainee.getPassword());
                ps.setBoolean(7, trainee.getIsActive());
                return ps;
            }, keyHolder
        );
        trainee.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return trainee;
    }

    @Override
    public void update(Trainee trainee) {
        jdbcTemplate.update(
            UPDATE_SQL,
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            trainee.getFirstName(),
            trainee.getLastName(),
            trainee.getUserName(),
            trainee.getPassword(),
            Boolean.TRUE.equals(trainee.getIsActive()) ? 1 : 0,
            trainee.getId()
        );
    }

}
