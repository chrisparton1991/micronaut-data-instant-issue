package com.example;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.jdbc.runtime.JdbcOperations;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.inject.Inject;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.H2)
public abstract class AnimalRepository implements CrudRepository<Animal, Integer> {

    @Inject private JdbcOperations jdbcOperations;

    public String getRawInstant(int id) {
        String sql = "SELECT created_at FROM Animal WHERE id = ?";

        return jdbcOperations.prepareStatement(sql, statement -> {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("created_at");
        });
    }
}
