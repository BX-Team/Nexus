package space.bxteam.nexus.core.configuration.main;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;
import space.bxteam.nexus.core.configuration.main.records.DatabaseConfig;

@Configuration
@Getter
public class PluginConfiguration {
    @Comment({
            "███╗░░██╗███████╗██╗░░██╗██╗░░░██╗░██████╗",
            "████╗░██║██╔════╝╚██╗██╔╝██║░░░██║██╔════╝",
            "██╔██╗██║█████╗░░░╚███╔╝░██║░░░██║╚█████╗░",
            "██║╚████║██╔══╝░░░██╔██╗░██║░░░██║░╚═══██╗",
            "██║░╚███║███████╗██╔╝╚██╗╚██████╔╝██████╔╝",
            "╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝░╚═════╝░╚═════╝░",
            "",
            "Discord server: https://discord.gg/p7cxhw7E2M",
            "Modrinth: https://modrinth.com/plugin/nexuss",
            "",
            "Should the plugin check for updates?"
    })
    private boolean checkForUpdates = true;

    @Comment("Plugin language")
    private String language = "en";

    @Comment("Plugin prefix")
    private String prefix = "&7[&6Nexus&7] ";

    @Comment("Database configuration")
    private DatabaseConfig database = new DatabaseConfig();
}
