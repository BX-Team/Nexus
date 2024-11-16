package space.bxteam.nexus.core.configuration;

import com.google.inject.Singleton;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.serializer.LanguageSerializer;
import space.bxteam.nexus.core.translation.Language;
import space.bxteam.nexus.core.utils.Logger;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

@Singleton
public class PluginConfigurationProvider {
    @Getter
    private volatile PluginConfiguration configuration;

    private final Path dataFolder;
    private final static String CONFIG_FILE = "config.yml";
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
        loadConfig();
    }

    public void loadConfig() {
        Optional<PluginConfiguration> config = loadConfiguration();
        this.configuration = config.orElseThrow(() ->
                new RuntimeException("Failed to load configuration file."));
    }

    private Optional<PluginConfiguration> loadConfiguration() {
        try {
            return Optional.of(YamlConfigurations.update(
                    this.dataFolder.resolve(CONFIG_FILE),
                    PluginConfiguration.class,
                    YamlConfigurationProperties.newBuilder()
                            .charset(StandardCharsets.UTF_8)
                            .addSerializer(Language.class, new LanguageSerializer())
                            .header(HEADER)
                            .build()));
        } catch (Exception e) {
            Logger.log("Could not load configuration file. Please check for syntax errors.", Logger.LogLevel.ERROR);
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
