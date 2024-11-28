package space.bxteam.nexus.core.configuration.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.ConfigurationManager;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class CommandsConfigProvider {
    @Getter
    private volatile CommandsConfig commandsConfig;

    private final Path dataFolder;
    private final ConfigurationManager configurationManager;

    @Inject
    public CommandsConfigProvider(@Named("dataFolder") Path dataFolder, ConfigurationManager configurationManager) {
        this.dataFolder = dataFolder;
        this.configurationManager = configurationManager;

        this.loadConfig();
    }

    private void loadConfig() {
        this.commandsConfig = this.configurationManager.create(CommandsConfig.class, new File(this.dataFolder.resolve("commands.yml").toString()));
    }
}
