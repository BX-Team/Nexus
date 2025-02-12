package org.bxteam.nexus.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import org.bxteam.nexus.NexusApiProvider;
import org.bxteam.nexus.core.configuration.ConfigurationManager;
import org.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import org.bxteam.nexus.core.configuration.plugin.ConfigModule;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.database.DatabaseClient;
import org.bxteam.nexus.core.database.DatabaseModule;
import org.bxteam.nexus.core.integration.IntegrationRegistry;
import org.bxteam.nexus.core.registration.litecommands.LiteCommandsRegistry;
import org.bxteam.nexus.core.multification.module.MultificationModule;
import org.bxteam.nexus.core.registration.component.ComponentRegistry;
import org.bxteam.nexus.core.scheduler.SchedulerSetup;
import org.bxteam.nexus.core.translation.TranslationProvider;
import org.bxteam.nexus.event.NexusInitializeEvent;

import java.sql.SQLException;

public class Nexus {
    private final PluginConfigurationProvider configurationProvider;
    private final Injector injector;

    public Nexus(Plugin plugin) {
        NexusEnvironment environment = new NexusEnvironment(plugin);

        ConfigurationManager configurationManager = new ConfigurationManager();
        this.configurationProvider = new PluginConfigurationProvider(plugin.getDataFolder().toPath(), configurationManager);

        this.injector = Guice.createInjector(
                        new NexusModule(this.configurationProvider, plugin, configurationManager),
                        new ConfigModule(),
                        new MultificationModule(),
                        new DatabaseModule(this.configurationProvider),
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
        this.injector.getInstance(LiteCommandsRegistry.class).onEnable();
        this.injector.getInstance(ComponentRegistry.class);

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));
        environment.finalizeLoading();
        plugin.getServer().getPluginManager().callEvent(new NexusInitializeEvent());
    }

    public void disable() {
        this.injector.getInstance(LiteCommandsRegistry.class).onDisable();
        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
