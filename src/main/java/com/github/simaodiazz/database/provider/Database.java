package com.github.simaodiazz.database.provider;

import com.github.simaodiazz.database.provider.configuration.DatabaseConfiguration;
import com.github.simaodiazz.database.provider.executor.DatabaseExecutor;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database {

    private final DatabaseConfiguration configuration;
    private final HikariDataSource dataSource;

    public Database(DatabaseConfiguration configuration) {
        this.configuration = configuration;
        this.dataSource = new HikariDataSource();
    }

    public abstract DatabaseType getType();

    public void close() {
        this.dataSource.close();
    }

    public Connection getConnection() {
        try (Connection connection = dataSource.getConnection()) {
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DatabaseConfiguration getConfiguration() {
        return configuration;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public DatabaseExecutor execute(String command) {
        return new DatabaseExecutor(getConnection(), command);
    }
}