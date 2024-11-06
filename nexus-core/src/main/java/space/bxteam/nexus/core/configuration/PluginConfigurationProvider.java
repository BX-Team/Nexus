package space.bxteam.nexus.core.configuration;

import com.google.inject.Singleton;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import space.bxteam.nexus.core.utils.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;
    private final Path dataFolder;
    private final static String HEADER = """
                                            ███╗░░██╗███████╗██╗░░██╗██╗░░░██╗░██████╗
                                            ████╗░██║██╔════╝╚██╗██╔╝██║░░░██║██╔════╝
                                            ██╔██╗██║█████╗░░░╚███╔╝░██║░░░██║╚█████╗░
                                            ██║╚████║██╔══╝░░░██╔██╗░██║░░░██║░╚═══██╗
                                            ██║░╚███║███████╗██╔╝╚██╗╚██████╔╝██████╔╝
                                            ╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝░╚═════╝░╚═════╝░
                                            
                                            Discord server: https://discord.gg/p7cxhw7E2M
                                            Modrinth: https://modrinth.com/plugin/nexuss
                                            """;

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
                            YamlConfigurationProperties.newBuilder()
                                    .charset(StandardCharsets.UTF_8)
                                    .header(HEADER)
                                    .build());
        } catch (Exception e) {
            Logger.log("Could not load configuration file. This can happen if you have made a mistake in the configuration file", Logger.LogLevel.ERROR);
            throw new RuntimeException("Could not load configuration file.", e);
        }
    }
}
