package space.bxteam.nexus.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.NexusApiProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseModule;
import space.bxteam.nexus.core.configuration.ConfigModule;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.environment.NexusEnvironment;
import space.bxteam.nexus.core.integration.IntegrationRegistry;
import space.bxteam.nexus.core.integration.bstats.MetricsModule;
import space.bxteam.nexus.core.integration.litecommands.LiteCommandsRegister;
import space.bxteam.nexus.core.scanner.register.ComponentRegister;
import space.bxteam.nexus.core.translation.TranslationManager;
import space.bxteam.nexus.core.translation.TranslationModule;
import space.bxteam.nexus.core.utils.Logger;

public class Nexus {
    private final PluginConfigurationProvider configurationProvider;
    private final Injector injector;

    public Nexus(Plugin plugin) {
        NexusEnvironment environment = new NexusEnvironment(plugin.getServer());

        this.configurationProvider = new PluginConfigurationProvider(plugin.getDataFolder().toPath());
        this.injector =
                Guice.createInjector(
                        new NexusModule(this.configurationProvider, plugin),
                        new ConfigModule(),
                        new TranslationModule(),
                        new DatabaseModule(this.configurationProvider),
                        new MetricsModule()
                );

        TranslationManager translationManager = this.injector.getInstance(TranslationManager.class);
        translationManager.create(configurationProvider.configuration().language());

        this.injector.getInstance(IntegrationRegistry.class).init();
        this.injector.getInstance(DatabaseClient.class).open();
        this.injector.getInstance(LiteCommandsRegister.class).onEnable();
        this.injector.getInstance(ComponentRegister.class);

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));
        environment.finalizeLoading();
    }

    public void disable() {
        Logger.log("Disabling Nexus...", Logger.LogLevel.INFO);

        this.injector.getInstance(LiteCommandsRegister.class).onDisable();
        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
