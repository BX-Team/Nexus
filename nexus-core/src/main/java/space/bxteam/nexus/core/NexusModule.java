package space.bxteam.nexus.core;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import space.bxteam.nexus.core.environment.meta.PluginVersionMeta;
import space.bxteam.nexus.core.environment.meta.PluginVersionMetaFactory;
import space.bxteam.nexus.core.feature.home.HomeServiceImpl;
import space.bxteam.nexus.core.feature.warp.WarpServiceImpl;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.integration.adventure.processors.AdventureLegacyColorPostProcessor;
import space.bxteam.nexus.core.integration.adventure.processors.AdventureLegacyColorPreProcessor;
import space.bxteam.nexus.core.integration.adventure.processors.AdventureUrlPostProcessor;
import space.bxteam.nexus.feature.home.HomeService;
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
        this.bind(AudienceProvider.class).toInstance(BukkitAudiences.create(this.plugin));
        this.bind(PluginVersionMeta.class).toInstance(PluginVersionMetaFactory.create(this.plugin));

        this.bind(MiniMessage.class).toInstance(MiniMessage.miniMessage());
        this.bind(MiniMessage.class)
                .annotatedWith(Names.named("colorMiniMessage"))
                .toInstance(MiniMessage.builder()
                        .postProcessor(new AdventureUrlPostProcessor())
                        .postProcessor(new AdventureLegacyColorPostProcessor())
                        .preProcessor(new AdventureLegacyColorPreProcessor())
                        .build());

        this.bind(WarpService.class).to(WarpServiceImpl.class);
        this.bind(HomeService.class).to(HomeServiceImpl.class);
    }
}
