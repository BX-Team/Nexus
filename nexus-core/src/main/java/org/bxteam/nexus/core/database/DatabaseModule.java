package org.bxteam.nexus.core.database;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.bxteam.nexus.core.database.clients.MariaDBClient;
import org.bxteam.nexus.core.database.clients.PostgreSQLClient;
import org.bxteam.nexus.core.database.clients.SQLiteClient;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.feature.home.database.HomeRepository;
import org.bxteam.nexus.core.feature.home.database.HomeRepositoryOrmLite;
import org.bxteam.nexus.core.feature.ignore.database.IgnoreRepository;
import org.bxteam.nexus.core.feature.ignore.database.IgnoreRepositoryOrmLite;
import org.bxteam.nexus.core.feature.jail.database.JailRepository;
import org.bxteam.nexus.core.feature.jail.database.JailRepositoryOrmLite;
import org.bxteam.nexus.core.feature.warp.database.WarpRepository;
import org.bxteam.nexus.core.feature.warp.database.WarpRepositoryOrmLite;

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
