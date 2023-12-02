package com.github.simaodiazz.database.provider.configuration.automate.impl;

import com.github.simaodiazz.database.provider.DatabaseType;
import com.github.simaodiazz.database.provider.configuration.DatabaseConfiguration;
import com.github.simaodiazz.database.provider.configuration.automate.DatabaseConfigurationAutomateFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class PaperDatabaseConfigurationAutomateFactory implements DatabaseConfigurationAutomateFactory {

    @Override
    public DatabaseConfiguration build(Object... args) {
        FileConfiguration configuration = (FileConfiguration) args[0];
        if (configuration == null)
            throw new NullPointerException("configuration is null");

        ConfigurationSection section = configuration.getConfigurationSection("database");
        if (section == null)
            throw new NullPointerException("section is null");

        DatabaseType type = DatabaseType.valueOf(section.getString("type").toUpperCase());
        String url = section.getString("url");
        String username = section.getString("username");
        String password = section.getString("password");

        return new DatabaseConfiguration(type, url, username, password);
    }
}
