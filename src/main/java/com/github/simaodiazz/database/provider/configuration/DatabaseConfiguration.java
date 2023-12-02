package com.github.simaodiazz.database.provider.configuration;

import com.github.simaodiazz.database.provider.DatabaseType;

public class DatabaseConfiguration {

    private final DatabaseType type;
    private final String url;
    private final String username;
    private final String password;

    public DatabaseConfiguration(DatabaseType type, String url, String username, String password) {
        this.type = type;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public DatabaseType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}