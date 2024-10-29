package space.bxteam.nexus.core.integration.bstats;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.Plugin;

public class MetricsModule extends AbstractModule {
    @Provides
    @Singleton
    @Inject
    public Metrics provideMetrics(Plugin plugin) {
        return new Metrics(plugin, 19684);
    }
}
