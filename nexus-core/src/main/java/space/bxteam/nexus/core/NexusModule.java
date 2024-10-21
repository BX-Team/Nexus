package space.bxteam.nexus.core;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import space.bxteam.nexus.core.feature.warp.WarpServiceImpl;
import space.bxteam.nexus.core.files.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.feature.warp.WarpService;

import java.nio.file.Path;

@RequiredArgsConstructor
public class NexusModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;
    private final Plugin plugin;

    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(PluginConfigurationProvider.class).toInstance(this.configurationProvider);
        this.bind(Server.class).toInstance(this.plugin.getServer());
        this.bind(PluginManager.class).toInstance(this.plugin.getServer().getPluginManager());
        this.bind(PluginDescriptionFile.class).toInstance(this.plugin.getDescription());
        this.bind(ServicesManager.class).toInstance(this.plugin.getServer().getServicesManager());
        this.bind(Path.class)
                .annotatedWith(Names.named("dataFolder"))
                .toInstance(this.plugin.getDataFolder().toPath());

        this.bind(WarpService.class).to(WarpServiceImpl.class);
    }
}
