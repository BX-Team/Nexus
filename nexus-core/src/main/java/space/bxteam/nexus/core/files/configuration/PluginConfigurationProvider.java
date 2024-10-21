package space.bxteam.nexus.core.files.configuration;

import com.google.inject.Singleton;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import space.bxteam.nexus.core.utils.LogUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;
    private final Path dataFolder;

    public PluginConfigurationProvider(Path dataFolder) {
        this.dataFolder = dataFolder;

        this.loadConfig();
    }

    public void loadConfig() {
        try {
            this.configuration =
                    YamlConfigurations.update(
                            this.dataFolder.resolve("config.yml"),
                            PluginConfiguration.class,
                            YamlConfigurationProperties.newBuilder().charset(StandardCharsets.UTF_8).build());
        } catch (Exception e) {
            LogUtil.log("Could not load configuration file. This can happen if you have made a mistake in the configuration file", LogUtil.LogLevel.ERROR);
            throw new RuntimeException("Could not load configuration file.", e);
        }
    }
}
