package space.bxteam.nexus.core.configuration.main.records;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class MariaDBConfig {
    private String jdbc = "jdbc:mariadb://localhost:3306/nexus";
    private String username = "root";
    private String password = "password";
}
