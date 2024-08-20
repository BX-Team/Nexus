package space.bxteam.nexus.core.database;

import com.google.common.base.Stopwatch;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.SneakyThrows;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.utils.LogUtil;

import java.io.File;
import java.sql.SQLException;

public class DatabaseManager {
    private final ConfigurationManager configurationProvider;
    private final File dataFolder;

    public HikariConfig hikariConfig = new HikariConfig();
    @Getter
    private HikariDataSource dataSource;

    @SneakyThrows
    public DatabaseManager(ConfigurationManager configurationProvider, File dataFolder) {
        this.configurationProvider = configurationProvider;
        this.dataFolder = dataFolder;
        connect();
    }

    public void connect() throws SQLException {
        Stopwatch stopwatch = Stopwatch.createStarted();

        DatabaseType databaseType = configurationProvider.configuration().database().type();

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
        hikariConfig.setMaximumPoolSize(5);

        switch (DatabaseType.valueOf(databaseType.toString().toUpperCase())) {
            case SQLITE -> {
                hikariConfig.setDriverClassName("org.sqlite.JDBC");
                hikariConfig.setJdbcUrl("jdbc:sqlite:" + dataFolder + File.separator + configurationProvider.configuration().database().sqlite().file());
            }
            case MARIADB -> {
                hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
                hikariConfig.setJdbcUrl(configurationProvider.configuration().database().mariadb().jdbc());
                hikariConfig.setUsername(configurationProvider.configuration().database().mariadb().username());
                hikariConfig.setPassword(configurationProvider.configuration().database().mariadb().password());
            }

            default -> throw new SQLException("Unknown database type: " + databaseType);
        }

        dataSource = new HikariDataSource(hikariConfig);

        createTables();

        LogUtil.log("Loaded database " + databaseType.toString().toLowerCase() + " in " + stopwatch.stop(), LogUtil.LogLevel.INFO);
    }

    public void close() {
        try {
            this.dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {

    }
}
