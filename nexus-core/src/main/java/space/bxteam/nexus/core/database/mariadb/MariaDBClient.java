package space.bxteam.nexus.core.database.mariadb;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseQueries;
import space.bxteam.nexus.core.database.statement.StatementBuilder;
import space.bxteam.nexus.core.configuration.plugin.PluginConfiguration;

import java.sql.SQLException;

@Singleton
public class MariaDBClient implements DatabaseClient, DatabaseQueries {
    private final Provider<PluginConfiguration> configurationProvider;
    @Getter
    private HikariDataSource dataSource;
    private final HikariConfig hikariConfig;

    @Inject
    public MariaDBClient(Provider<PluginConfiguration> configurationProvider) {
        this.configurationProvider = configurationProvider;

        this.hikariConfig = new HikariConfig();
    }

    @Override
    public boolean available() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public StatementBuilder newBuilder(String statement) {
        try {
            return new StatementBuilder(this.dataSource.getConnection())
                    .statement(statement)
                    .logging(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DatabaseQueries queries() {
        return this;
    }

    @Override
    public void open() {
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl(this.configurationProvider.get().database().mariadb().jdbc());
        hikariConfig.setUsername(this.configurationProvider.get().database().mariadb().username());
        hikariConfig.setPassword(this.configurationProvider.get().database().mariadb().password());

        this.dataSource = new HikariDataSource(this.hikariConfig);

        this.createTables();
    }

    public void close() {
        if (this.dataSource == null) return;
        this.dataSource.close();
    }

    @Override
    public void createTables() {
        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `players` (" +
                        "`uuid` VARCHAR(255) NOT NULL UNIQUE," +
                        "`name` VARCHAR(255) NOT NULL," +
                        "`ip` VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY(`uuid`)" +
                        ");"
        ).execute();

        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `homes` (" +
                        "`owner` VARCHAR(255) NOT NULL," +
                        "`name` VARCHAR(255) NOT NULL," +
                        "`position` VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY(`owner`, `name`)" +
                        ");"
        ).execute();

        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `warps` (" +
                        "`name` VARCHAR(255) NOT NULL UNIQUE," +
                        "`position` VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY(`name`)" +
                        ");"
        ).execute();

        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `jailed_players` (" +
                        "`id` VARCHAR(255) NOT NULL," +
                        "`jailedAt` VARCHAR(255) NOT NULL," +
                        "`duration` VARCHAR(255) NOT NULL," +
                        "`jailedBy` VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY(`id`)" +
                        ");"
        ).execute();
    }
}
