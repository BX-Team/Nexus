package space.bxteam.nexus.core;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.Plugin;
import space.bxteam.nexus.NexusApiProvider;
import space.bxteam.nexus.core.database.DatabaseClient;
import space.bxteam.nexus.core.database.DatabaseModule;
import space.bxteam.nexus.core.configuration.ConfigModule;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.integration.bstats.MetricsModule;
import space.bxteam.nexus.core.translation.TranslationModule;
import space.bxteam.nexus.core.utils.LogUtil;

public class Nexus {
    private PluginConfigurationProvider configurationProvider;
    private Injector injector;

    public Nexus(Plugin plugin) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        this.configurationProvider = new PluginConfigurationProvider(plugin.getDataFolder().toPath());
        this.injector =
                Guice.createInjector(
                        new NexusModule(this.configurationProvider, plugin),
                        new ConfigModule(),
                        new TranslationModule(this.configurationProvider, plugin.getDataFolder().toPath().resolve("languages")),
                        new DatabaseModule(this.configurationProvider),
                        new MetricsModule());

        this.injector.getInstance(DatabaseClient.class).open();

        NexusApiProvider.initialize(new NexusApiImpl(this.injector));

        LogUtil.log("Enabled Nexus in " + stopwatch.stop(), LogUtil.LogLevel.INFO);
    }

    public void disable() {
        LogUtil.log("Disabling Nexus...", LogUtil.LogLevel.INFO);

        this.injector.getInstance(DatabaseClient.class).close();

        NexusApiProvider.shutdown();
    }
}
