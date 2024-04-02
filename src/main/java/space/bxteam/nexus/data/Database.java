package space.bxteam.nexus.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.utils.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Objects;

public class Database {
    public final @NotNull HikariConfig hikariConfig = new HikariConfig();
    public HikariDataSource dbSource;

    public Database(@NotNull JavaPlugin plugin) {
        setupDatabaseSource();
        try {
            initTables();
        }
        catch (@NotNull SQLException | @NotNull IOException e) {
            Logger.log("An error occurred while initializing the database!", Logger.LogLevel.ERROR, true);
            e.printStackTrace();
            Nexus.getInstance().getServer().getPluginManager().disablePlugin(Nexus.getInstance());
        }
    }

    /**
     * Get the database source
     */
    private void setupDatabaseSource() {
        switch (Nexus.getInstance().getConfigString("database.type")) {
            case "sqlite" -> {
                hikariConfig.setDriverClassName("org.sqlite.JDBC");
                hikariConfig.setJdbcUrl("jdbc:sqlite:" + Nexus.getInstance().getDataFolder() + File.separator + Nexus.getInstance().getConfigString("database.sqlite.file"));
            }
            case "mysql" -> {
                hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                hikariConfig.setJdbcUrl(Nexus.getInstance().getConfigString("database.mysql.jdbc"));
                hikariConfig.setUsername(Nexus.getInstance().getConfigString("database.mysql.username"));
                hikariConfig.setPassword(Nexus.getInstance().getConfigString("database.mysql.password"));
            }
            default -> {
                Logger.log("Invalid database type! Please check your config.yml", Logger.LogLevel.ERROR, true);
                Nexus.getInstance().getServer().getPluginManager().disablePlugin(Nexus.getInstance());
                return;
            }
        }

        dbSource = new HikariDataSource(hikariConfig);
    }

    /**
     * Initialize the database tables
     */
    private void initTables() throws @NotNull SQLException, @NotNull IOException {
        final @NotNull HashMap<@NotNull String, @NotNull String> initFiles = new HashMap<>() {{
            put("sqlite", "databases/sqlite.sql");
            //put("mysql", "databases/mysql.sql");
        }};
        final @NotNull String dbType = Nexus.getInstance().getConfigString("database.type");
        if (!initFiles.containsKey(dbType)) {
            Logger.log("Invalid database type! Please check your config.yml", Logger.LogLevel.ERROR, true);
            Nexus.getInstance().getServer().getPluginManager().disablePlugin(Nexus.getInstance());
            return;
        }
        @NotNull String setupFile = initFiles.get(dbType);
        @NotNull String query;
        try (@NotNull InputStream stream = Objects.requireNonNull(Nexus.getInstance().getResource(setupFile))) {
            query = new @NotNull String(stream.readAllBytes());
        } catch (@NotNull IOException e) {
            Logger.log("An error occurred while reading the database setup file!", Logger.LogLevel.ERROR, true);
            throw e;
        }
        final @NotNull String[] queries = query.split(";");
        for (@NotNull String query1 : queries) {
            query1 = query1.stripTrailing().stripIndent().replaceAll("^\\s+(?:--.+)*", "");
            if (query1.isBlank()) continue;
            try (final @NotNull Connection conn = dbSource.getConnection();
                 final @NotNull PreparedStatement stmt = conn.prepareStatement(query1)) {
                stmt.execute();
            }
        }
        Logger.log("Database initialized", Logger.LogLevel.INFO, false);
    }
}
