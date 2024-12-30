package space.bxteam.nexus.core.database;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.core.database.clients.MariaDBClient;
import space.bxteam.nexus.core.database.clients.PostgreSQLClient;
import space.bxteam.nexus.core.database.clients.SQLiteClient;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.feature.home.database.HomeRepository;
import space.bxteam.nexus.core.feature.home.database.HomeRepositoryOrmLite;
import space.bxteam.nexus.core.feature.ignore.database.IgnoreRepository;
import space.bxteam.nexus.core.feature.ignore.database.IgnoreRepositoryOrmLite;
import space.bxteam.nexus.core.feature.jail.database.JailRepository;
import space.bxteam.nexus.core.feature.jail.database.JailRepositoryOrmLite;
import space.bxteam.nexus.core.feature.warp.database.WarpRepository;
import space.bxteam.nexus.core.feature.warp.database.WarpRepositoryOrmLite;

@RequiredArgsConstructor
public class DatabaseModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;

    @Override
    protected void configure() {
        DatabaseType type = this.configurationProvider.configuration().database().type();

        switch (DatabaseType.valueOf(type.toString().toUpperCase())) {
            case SQLITE -> this.bind(DatabaseClient.class).to(SQLiteClient.class);
            case MARIADB -> this.bind(DatabaseClient.class).to(MariaDBClient.class);
            case POSTGRESQL -> this.bind(DatabaseClient.class).to(PostgreSQLClient.class);
        }

        this.bind(HomeRepository.class).to(HomeRepositoryOrmLite.class);
        this.bind(IgnoreRepository.class).to(IgnoreRepositoryOrmLite.class);
        this.bind(JailRepository.class).to(JailRepositoryOrmLite.class);
        this.bind(WarpRepository.class).to(WarpRepositoryOrmLite.class);
    }
}
