package space.bxteam.nexus.core.configuration.main.records;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class SQLiteConfig {
    private String file = "nexus.db";
}
