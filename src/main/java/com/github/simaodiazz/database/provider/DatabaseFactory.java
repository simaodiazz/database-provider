package com.github.simaodiazz.database.provider;

import com.github.simaodiazz.database.provider.configuration.DatabaseConfiguration;
import com.github.simaodiazz.database.provider.impl.MySQL;

public class DatabaseFactory {

    private static DatabaseFactory instance;

    public Database build(DatabaseConfiguration configuration) {
        return new MySQL(configuration);
    }

    public static DatabaseFactory getInstance() {
        if (instance == null) instance = new DatabaseFactory();
        return instance;
    }
}