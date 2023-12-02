package com.github.simaodiazz.database.provider.configuration.automate;

import com.github.simaodiazz.database.provider.configuration.automate.impl.PaperDatabaseConfigurationAutomateFactory;

public class DatabaseConfigurationAutomateClient {

    private static DatabaseConfigurationAutomateClient instance;

    private final PaperDatabaseConfigurationAutomateFactory paperFactory;

    public DatabaseConfigurationAutomateClient() {
        this.paperFactory = new PaperDatabaseConfigurationAutomateFactory();
    }

    public DatabaseConfigurationAutomateFactory getFactory(DatabaseConfigurationAutomateType type) {
        return paperFactory;
    }

    public static DatabaseConfigurationAutomateClient getInstance() {
        if (instance == null) instance = new DatabaseConfigurationAutomateClient();
        return instance;
    }
}