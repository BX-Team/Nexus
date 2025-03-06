package org.bxteam.nexus.core.configuration.plugin;

import com.google.inject.Singleton;
import lombok.Getter;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.nexus.core.configuration.ConfigurationManager;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;

    private final Path dataFolder;
    private final ConfigurationManager configurationManager;
    private final ExtendedLogger logger;

    public PluginConfigurationProvider(Path dataFolder, ConfigurationManager configurationManager, ExtendedLogger logger) {
        this.dataFolder = dataFolder;
        this.configurationManager = configurationManager;
        this.logger = logger;

        this.createConfig();
    }

    private void createConfig() {
        File configFile = this.dataFolder.resolve("config.yml").toFile();

        try {
            logger.info("Loading confuguration...");
            this.configuration = configurationManager.create(PluginConfiguration.class, configFile);
        } catch (Exception e) {
            logger.error("Could not create config.yml file");
            e.printStackTrace();
        }
    }
}
