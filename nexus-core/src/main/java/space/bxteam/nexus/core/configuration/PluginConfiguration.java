package space.bxteam.nexus.core.configuration;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

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
}
