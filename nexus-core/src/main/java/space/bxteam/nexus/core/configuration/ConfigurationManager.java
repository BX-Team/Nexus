package space.bxteam.nexus.core.configuration;

import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.main.PluginConfiguration;

import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Singleton
public class ConfigurationManager {
    @Getter
    private volatile PluginConfiguration configuration;
    private final Path dataFolder;

    public ConfigurationManager(Path dataFolder) {
        this.dataFolder = dataFolder;
        this.loadConfig();
    }

    public void loadConfig() {
        try {
            this.configuration = YamlConfigurations.update(
                    this.dataFolder.resolve("config.yml"),
                    PluginConfiguration.class,
                    YamlConfigurationProperties.newBuilder().charset(StandardCharsets.UTF_8).build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration files", e);
        }
    }
}
