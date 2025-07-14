package org.bxteam.nexus.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.commons.logger.LogLevel;
import org.bxteam.commons.logger.appender.Appender;
import org.bxteam.commons.logger.appender.ConsoleAppender;
import org.bxteam.commons.logger.appender.JsonAppender;
import org.bxteam.nexus.NexusApiProvider;
import org.bxteam.nexus.core.configuration.ConfigurationManager;
import org.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import org.bxteam.nexus.core.configuration.plugin.ConfigModule;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.database.DatabaseClient;
import org.bxteam.nexus.core.database.DatabaseModule;
import org.bxteam.nexus.core.integration.IntegrationRegistry;
import org.bxteam.nexus.core.integration.litecommands.LiteCommandsSetup;
import org.bxteam.nexus.core.multification.module.MultificationModule;
import org.bxteam.nexus.core.annotations.AnnotationSetup;
import org.bxteam.nexus.core.scheduler.SchedulerSetup;
import org.bxteam.nexus.core.translation.TranslationProvider;
import org.bxteam.nexus.event.NexusInitializeEvent;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class Nexus {
    private final Injector injector;

    public Nexus(Plugin plugin) {
        Appender consoleAppender = new ConsoleAppender("[{loggerName}] {logLevel}: {message}");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        File logsFile = new File("plugins/Nexus/logs/nexus-logs-" + date + ".txt");
        if (!logsFile.exists()) {
            try {
                logsFile.getParentFile().mkdirs();
                logsFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JsonAppender jsonAppender = new JsonAppender(false, false, true, logsFile.getPath());
        ExtendedLogger logger = new ExtendedLogger("Nexus", LogLevel.INFO, List.of(consoleAppender, jsonAppender), new ArrayList<>());

        NexusEnvironment environment = new NexusEnvironment(logger);

        ConfigurationManager configurationManager = new ConfigurationManager();
        PluginConfigurationProvider configurationProvider = new PluginConfigurationProvider(plugin.getDataFolder().toPath(), configurationManager, logger);

        this.injector = Guice.createInjector(
                        new NexusModule(configurationProvider, plugin, configurationManager, logger),
                        new ConfigModule(),
                        new MultificationModule(),
                        new DatabaseModule(configurationProvider),
                        new SchedulerSetup(plugin)
        );

        try {
            this.injector.getInstance(DatabaseClient.class).open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.injector.getInstance(TranslationProvider.class);
        this.injector.getInstance(IntegrationRegistry.class).init();
        this.injector.getInstance(CommandsConfigProvider.class);
        this.injector.getInstance(LiteCommandsSetup.class).onEnable();
        this.injector.getInstance(AnnotationSetup.class);

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));
        environment.finalizeLoading();
        plugin.getServer().getPluginManager().callEvent(new NexusInitializeEvent());
    }

    public void disable() {
        this.injector.getInstance(LiteCommandsSetup.class).onDisable();
        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
