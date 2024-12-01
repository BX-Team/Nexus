package space.bxteam.nexus.core.configuration.plugin;

import com.google.inject.Singleton;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.seriazlier.position.PositionSerdesPack;
import space.bxteam.nexus.core.utils.Logger;

import java.nio.file.Path;
import java.util.Optional;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;

    private final Path dataFolder;
    private final static String CONFIG_FILE = "config.yml";

    public PluginConfigurationProvider(Path dataFolder) {
        this.dataFolder = dataFolder;
        loadConfig();
    }

    public void loadConfig() {
        Optional<PluginConfiguration> config = loadConfiguration();
        this.configuration = config.orElseThrow(() ->
                new RuntimeException("Failed to load configuration file."));
    }

    private Optional<PluginConfiguration> loadConfiguration() {
        try {
            return Optional.of(ConfigManager.create(PluginConfiguration.class, (config) -> {
                config.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesCommons(), new PositionSerdesPack());
                config.withBindFile(dataFolder.resolve(CONFIG_FILE));
                config.withRemoveOrphans(true);
                config.saveDefaults();
                config.load(true);
            }));
        } catch (Exception e) {
            Logger.log("Could not load configuration file. Please check for syntax errors.", Logger.LogLevel.ERROR);
            return Optional.empty();
        }
    }
}
