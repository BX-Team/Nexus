package space.bxteam.nexus.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.NexusApiProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseModule;
import space.bxteam.nexus.core.configuration.plugin.ConfigModule;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.integration.IntegrationRegistry;
import space.bxteam.nexus.core.integration.bstats.MetricsModule;
import space.bxteam.nexus.core.integration.litecommands.LiteCommandsRegister;
import space.bxteam.nexus.core.scanner.register.ComponentRegister;
import space.bxteam.nexus.core.translation.TranslationProvider;
import space.bxteam.nexus.core.utils.Logger;
import space.bxteam.nexus.event.NexusInitializeEvent;

public class Nexus {
    private final PluginConfigurationProvider configurationProvider;
    private final Injector injector;

    public Nexus(Plugin plugin) {
        NexusEnvironment environment = new NexusEnvironment(plugin);

        this.configurationProvider = new PluginConfigurationProvider(plugin.getDataFolder().toPath());
        this.injector = Guice.createInjector(
                        new NexusModule(this.configurationProvider, plugin),
                        new ConfigModule(),
                        new DatabaseModule(this.configurationProvider),
                        new MetricsModule()
        );

        this.injector.getInstance(TranslationProvider.class);
        this.injector.getInstance(IntegrationRegistry.class).init();
        this.injector.getInstance(DatabaseClient.class).open();
        this.injector.getInstance(LiteCommandsRegister.class).onEnable();
        this.injector.getInstance(ComponentRegister.class);

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));
        environment.finalizeLoading();
        plugin.getServer().getPluginManager().callEvent(new NexusInitializeEvent());
    }

    public void disable() {
        Logger.log("Disabling Nexus...", Logger.LogLevel.INFO);

        this.injector.getInstance(LiteCommandsRegister.class).onDisable();
        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
