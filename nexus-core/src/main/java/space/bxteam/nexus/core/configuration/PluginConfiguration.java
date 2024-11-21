package space.bxteam.nexus.core.configuration;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import space.bxteam.nexus.core.translation.Language;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@SuppressWarnings("FieldMayBeFinal")
@Header("███╗░░██╗███████╗██╗░░██╗██╗░░░██╗░██████╗")
@Header("████╗░██║██╔════╝╚██╗██╔╝██║░░░██║██╔════╝")
@Header("██╔██╗██║█████╗░░░╚███╔╝░██║░░░██║╚█████╗░")
@Header("██║╚████║██╔══╝░░░██╔██╗░██║░░░██║░╚═══██╗")
@Header("██║░╚███║███████╗██╔╝╚██╗╚██████╔╝██████╔╝")
@Header("╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝░╚═════╝░╚═════╝░")
@Header("")
@Header("Discord server: https://discord.gg/p7cxhw7E2M")
@Header("Modrinth: https://modrinth.com/plugin/nexuss")
public class PluginConfiguration extends OkaeriConfig {
    @Comment("Should the plugin check for updates?")
    private boolean checkForUpdates = true;

    @Comment("Plugin prefix")
    private String prefix = "&7[&6Nexus&7] ";

    @Comment("Plugin language")
    private Language language = Language.EN;

    @Comment({"", "Database configuration"})
    private DatabaseConfig database = new DatabaseConfig();

    @Getter
    public static class DatabaseConfig extends OkaeriConfig {
        @Comment({
                "Select here the database you want to use",
                "The following databases are supported:",
                " - sqlite - (default) stores all data in a local file",
                " - mariadb - allows using a remote database"
        })
        private String type = "sqlite";

        @Comment({"", "SQLite configuration"})
        private SQLiteConfig sqlite = new SQLiteConfig();

        @Getter
        public static class SQLiteConfig extends OkaeriConfig {
            private String file = "nexus.db";
        }

        @Comment({"", "MariaDB configuration"})
        private MariaDBConfig mariadb = new MariaDBConfig();

        @Getter
        public static class MariaDBConfig extends OkaeriConfig {
            private String jdbc = "jdbc:mariadb://localhost:3306/nexus";
            private String username = "root";
            private String password = "password";
        }
    }

    @Comment("")
    private Items items = new Items();

    @Getter
    public static class Items extends OkaeriConfig {
        @Comment("Use unsafe enchantments? Allows you to apply custom enchants to various items")
        private boolean unsafeEnchantments = true;

        @Comment({"", "The default item give amount, when no amount is specified in the command."})
        private int defaultGiveAmount = 1;
    }

    @Comment("")
    private Homes homes = new Homes();

    @Getter
    public static class Homes extends OkaeriConfig {
        @Comment("Default home name")
        private String defaultHomeName = "home";

        @Comment({"", "Maximum amount of homes per permission"})
        private Map<String, Integer> maxHomes = new LinkedHashMap<>() {
            {
                put("nexus.home.default", 1);
                put("nexus.home.extended", 2);
            }
        };
    }

    @Comment("")
    private Chat chat = new Chat();

    @Getter
    public static class Chat extends OkaeriConfig {
        @Comment("Is the slowmode enabled?")
        private boolean slowModeEnabled = true;
        @Comment("Chat slowmode time in seconds")
        private Duration slowMode = Duration.ofSeconds(5);

        @Comment("How many lines should be cleared when using the /chat clear command")
        private int clearLines = 100;
    }
}
