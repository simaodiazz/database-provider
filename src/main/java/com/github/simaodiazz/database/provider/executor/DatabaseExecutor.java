package com.github.simaodiazz.database.provider.executor;

import com.github.simaodiazz.database.provider.adapter.DatabaseAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseExecutor {

    private final static ExecutorService service = Executors.newFixedThreadPool(10);

    private final Connection connection;
    private final String command;

    public DatabaseExecutor(Connection connection, String command) {
        this.connection = connection;
        this.command = command;
    }

    public <T> CompletableFuture<Set<T>> readMany(Consumer<PreparedStatement> consumer, Function<ResultSet, T> function) {
        return CompletableFuture.supplyAsync(() -> {
            Set<T> set = new HashSet<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                consumer.accept(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        T element = function.apply(resultSet);
                        set.add(element);
                    }
                }
                return set;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<Set<T>> readMany(Function<ResultSet, T> function) {
        return CompletableFuture.supplyAsync(() -> {
            Set<T> set = new HashSet<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        T element = function.apply(resultSet);
                        set.add(element);
                    }
                }
                return set;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<Set<T>> readManyWithAdapter(Consumer<PreparedStatement> consumer, DatabaseAdapter<T> adapter) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                consumer.accept(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return adapter.adaptAll(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<Set<T>> readManyWithAdapter(DatabaseAdapter<T> adapter) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return adapter.adaptAll(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<T> readOne(Consumer<PreparedStatement> consumer, Function<ResultSet, T> function) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!resultSet.next()) return null;
                    return function.apply(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<T> readOne(Function<ResultSet, T> function) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (!resultSet.next()) return null;
                    return function.apply(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public <T> CompletableFuture<T> readOneWithAdapter(Consumer<PreparedStatement> consumer, DatabaseAdapter<T> adapter) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                consumer.accept(preparedStatement);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return adapter.adapt(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public <T> CompletableFuture<T> readOneWithAdapter(DatabaseAdapter<T> adapter) {
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return adapter.adapt(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void write(Consumer<PreparedStatement> consumer) {
        CompletableFuture.runAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                consumer.accept(preparedStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }

    public void write() {
        CompletableFuture.runAsync(() -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, service);
    }
}