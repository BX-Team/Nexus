package space.bxteam.nexus.core.database;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.database.mariadb.MariaDBClient;
import space.bxteam.nexus.core.database.sqlite.SQLiteClient;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;

@RequiredArgsConstructor
public class DatabaseModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;

    @Override
    protected void configure() {
        if (this.configurationProvider.configuration().database().type() == DatabaseType.MARIADB) {
            this.bind(DatabaseClient.class).to(MariaDBClient.class);
        } else {
            this.bind(DatabaseClient.class).to(SQLiteClient.class);
        }
    }
}
