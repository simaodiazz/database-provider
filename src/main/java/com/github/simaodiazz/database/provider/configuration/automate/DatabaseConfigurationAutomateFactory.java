package com.github.simaodiazz.database.provider.configuration.automate;

import com.github.simaodiazz.database.provider.configuration.DatabaseConfiguration;

public interface DatabaseConfigurationAutomateFactory {

    DatabaseConfiguration build(Object... args);

}