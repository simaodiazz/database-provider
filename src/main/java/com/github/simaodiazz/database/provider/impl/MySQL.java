package com.github.simaodiazz.database.provider.impl;

import com.github.simaodiazz.database.provider.Database;
import com.github.simaodiazz.database.provider.DatabaseType;
import com.github.simaodiazz.database.provider.configuration.DatabaseConfiguration;

public final class MySQL extends Database {

    public MySQL(DatabaseConfiguration configuration) {
        super(configuration);
        this.getDataSource().setJdbcUrl(this.getConfiguration().getUrl());
        this.getDataSource().setUsername(this.getConfiguration().getUsername());
        this.getDataSource().setPassword(this.getConfiguration().getPassword());
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MYSQL;
    }
}
