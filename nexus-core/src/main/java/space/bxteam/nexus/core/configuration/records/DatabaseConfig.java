package space.bxteam.nexus.core.configuration.records;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class DatabaseConfig {
    @Comment({
            "Select here the database you want to use",
            "The following databases are supported:",
            " - sqlite - (default) stores all data in a local file",
            " - mariadb - allows using a remote database"
    })
    private String type = "sqlite";

    @Comment("SQLite configuration")
    private SQLiteConfig sqlite = new SQLiteConfig();

    @Comment("MariaDB configuration")
    private MariaDBConfig mariadb = new MariaDBConfig();
}
