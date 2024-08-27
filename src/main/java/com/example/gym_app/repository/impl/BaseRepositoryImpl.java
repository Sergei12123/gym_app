package com.example.gym_app.repository.impl;

import com.example.gym_app.component.KeyHolderGenerator;
import com.example.gym_app.entity.BaseEntity;
import com.example.gym_app.exception.InitializeDbException;
import com.example.gym_app.repository.BaseRepository;
import com.example.gym_app.utils.StringCaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class BaseRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected KeyHolderGenerator keyHolderGenerator;

    @Autowired
    public void setKeyHolderGenerator(KeyHolderGenerator keyHolderGenerator) {
        this.keyHolderGenerator = keyHolderGenerator;
    }

    public void initializeDb(final String pathToScript) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToScript))) {
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }
            Arrays.stream(script.toString().split(";")).filter(StringUtils::isNotBlank).forEach(jdbcTemplate::execute);
        } catch (IOException e) {
            throw new InitializeDbException(e);
        }
    }

    public List<T> findAll() {
        String sql = "SELECT * FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName());
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(getEntityClass()));
    }

    public T getById(final Long id) {
        String sql = "SELECT * FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName()) + " WHERE id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(getEntityClass()));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM " + StringCaseUtil.convertToSnakeCase(getEntityClass().getSimpleName()) + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
