package space.bxteam.nexus.core.configuration.plugin;

import com.google.inject.Singleton;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.utils.Logger;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;

    private final Path dataFolder;
    private final ConfigurationManager configurationManager;

    public PluginConfigurationProvider(Path dataFolder, ConfigurationManager configurationManager) {
        this.dataFolder = dataFolder;
        this.configurationManager = configurationManager;

        this.createConfig();
    }

    private void createConfig() {
        File configFile = this.dataFolder.resolve("config.yml").toFile();

        try {
            this.configuration = configurationManager.create(PluginConfiguration.class, configFile);
        } catch (Exception e) {
            Logger.log("Could not create config.yml file", Logger.LogLevel.ERROR);
            e.printStackTrace();
        }
    }
}
