package space.bxteam.nexus.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.NexusApiProvider;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.configuration.commands.CommandsConfigProvider;
import space.bxteam.nexus.core.configuration.plugin.ConfigModule;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseModule;
import space.bxteam.nexus.core.integration.IntegrationRegistry;
import space.bxteam.nexus.core.integration.litecommands.LiteCommandsRegister;
import space.bxteam.nexus.core.multification.module.MultificationModule;
import space.bxteam.nexus.core.scanner.register.ComponentRegister;
import space.bxteam.nexus.core.scheduler.SchedulerSetup;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.event.NexusInitializeEvent;

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
        this.injector.getInstance(LiteCommandsRegister.class).onEnable();
        this.injector.getInstance(ComponentRegister.class);

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));
        environment.finalizeLoading();
        plugin.getServer().getPluginManager().callEvent(new NexusInitializeEvent());
    }

    public void disable() {
        this.injector.getInstance(LiteCommandsRegister.class).onDisable();
        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
