package space.bxteam.nexus.core.files.configuration;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import space.bxteam.nexus.core.files.configuration.records.DatabaseConfig;

@Configuration
@Getter
public class PluginConfiguration {
    @Comment("Should the plugin check for updates?")
    private boolean checkForUpdates = true;

    @Comment("Plugin prefix")
    private String prefix = "&7[&6Nexus&7] ";

    @Comment("Database configuration")
    private DatabaseConfig database = new DatabaseConfig();
}
