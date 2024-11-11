package space.bxteam.nexus.core.database.sqlite;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseQueries;
import space.bxteam.nexus.core.database.statement.StatementBuilder;
import space.bxteam.nexus.core.configuration.PluginConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SQLiteClient implements DatabaseClient, DatabaseQueries {
    @Named("dataFolder")
    private final Path dataDirectory;

    private final Provider<PluginConfiguration> configurationProvider;

    private Path databaseFile;
    private HikariDataSource dataSource;

    @SneakyThrows
    @Override
    public void open() {
        this.databaseFile = this.dataDirectory.resolve(this.configurationProvider.get().database().sqlite().file());

        if (!Files.exists(this.databaseFile)) {
            Files.createFile(this.databaseFile);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setPoolName("Nexus");
        hikariConfig.setDriverClassName("org.sqlite.JDBC");
        hikariConfig.setJdbcUrl("jdbc:sqlite:" + this.databaseFile.toAbsolutePath());

        this.dataSource = new HikariDataSource(hikariConfig);

        this.createTables();
    }

    @Override
    public void close() {
        this.dataSource.close();
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
                        "`owner` TEXT NOT NULL," +
                        "`name` TEXT NOT NULL," +
                        "`world` TEXT NOT NULL," +
                        "`x` REAL NOT NULL," +
                        "`y` REAL NOT NULL," +
                        "`z` REAL NOT NULL," +
                        "`yaw` REAL NOT NULL," +
                        "`pitch` REAL NOT NULL," +
                        "PRIMARY KEY(`owner`, `name`)" +
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
