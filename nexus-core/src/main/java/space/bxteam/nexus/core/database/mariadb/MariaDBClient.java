package space.bxteam.nexus.core.database.mariadb;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseQueries;
import space.bxteam.nexus.core.database.statement.StatementBuilder;
import space.bxteam.nexus.core.configuration.PluginConfiguration;

import java.sql.SQLException;

@Singleton
public class MariaDBClient implements DatabaseClient, DatabaseQueries {
    private final Provider<PluginConfiguration> configurationProvider;
    @Getter
    private HikariDataSource dataSource;
    private final HikariConfig hikariConfig;

    @Inject
    public MariaDBClient(Provider<PluginConfiguration> configurationProvider, JavaPlugin plugin) {
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
                        "`uuid` TEXT NOT NULL UNIQUE," +
                        "`name` TEXT NOT NULL," +
                        "`ip` TEXT NOT NULL," +
                        "PRIMARY KEY(`uuid`)" +
                        ");"
        ).execute();

        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `homes` (" +
                        "`uuid` TEXT NOT NULL," +
                        "`name` TEXT NOT NULL," +
                        "`world` TEXT NOT NULL," +
                        "`x` REAL NOT NULL," +
                        "`y` REAL NOT NULL," +
                        "`z` REAL NOT NULL," +
                        "`yaw` REAL NOT NULL," +
                        "`pitch` REAL NOT NULL," +
                        "PRIMARY KEY(`uuid`, `name`)" +
                        ");"
        ).execute();

        this.newBuilder(
                "CREATE TABLE IF NOT EXISTS `warps` (" +
                        "`name` TEXT NOT NULL UNIQUE," +
                        "`world` TEXT NOT NULL," +
                        "`x` REAL NOT NULL," +
                        "`y` REAL NOT NULL," +
                        "`z` REAL NOT NULL," +
                        "`yaw` REAL NOT NULL," +
                        "`pitch` REAL NOT NULL," +
                        "PRIMARY KEY(`name`)" +
                        ");"
        ).execute();
    }
}
