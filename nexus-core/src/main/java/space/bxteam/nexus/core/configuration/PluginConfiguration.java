package space.bxteam.nexus.core.configuration;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class PluginConfiguration {
    @Comment("Should the plugin check for updates?")
    private boolean checkForUpdates = true;

    @Comment("Plugin prefix")
    private String prefix = "&7[&6Nexus&7] ";

    @Comment("Plugin language")
    private String language = "en";

    @Comment({"", "Database configuration"})
    private DatabaseConfig database = new DatabaseConfig();

    @Getter
    @Configuration
    public static class DatabaseConfig {
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
        @Configuration
        public static class SQLiteConfig {
            private String file = "nexus.db";
        }

        @Comment({"", "MariaDB configuration"})
        private MariaDBConfig mariadb = new MariaDBConfig();

        @Getter
        @Configuration
        public static class MariaDBConfig {
            private String jdbc = "jdbc:mariadb://localhost:3306/nexus";
            private String username = "root";
            private String password = "password";
        }
    }

    private Items items = new Items();

    @Getter
    @Configuration
    public static class Items {
        @Comment("Use unsafe enchantments? Allows you to apply custom enchants to various items")
        private boolean unsafeEnchantments = true;

        @Comment({"", "The default item give amount, when no amount is specified in the command."})
        private int defaultGiveAmount = 1;
    }

    private Homes homes = new Homes();

    @Getter
    @Configuration
    public static class Homes {
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
}
