package org.bxteam.nexus.core.configuration.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.nexus.core.configuration.ConfigurationManager;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class CommandsConfigProvider {
    @Getter
    private volatile CommandsConfig commandsConfig;

    private final Path dataFolder;
    private final ConfigurationManager configurationManager;
    private final ExtendedLogger logger;

    @Inject
    public CommandsConfigProvider(@Named("dataFolder") Path dataFolder, ConfigurationManager configurationManager, ExtendedLogger logger) {
        this.dataFolder = dataFolder;
        this.configurationManager = configurationManager;
        this.logger = logger;

        this.loadConfig();
    }

    private void loadConfig() {
        File commandsFile = this.dataFolder.resolve("commands.yml").toFile();

        try {
            this.commandsConfig = configurationManager.create(CommandsConfig.class, commandsFile);
        } catch (Exception e) {
            logger.error("Could not create commands.yml file");
            e.printStackTrace();
        }
    }
}
