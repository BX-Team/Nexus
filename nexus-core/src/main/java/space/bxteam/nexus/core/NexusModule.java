package space.bxteam.nexus.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
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
import space.bxteam.commons.adventure.processor.AdventureLegacyColorPostProcessor;
import space.bxteam.commons.adventure.processor.AdventureLegacyColorPreProcessor;
import space.bxteam.commons.adventure.processor.AdventureUrlPostProcessor;
import space.bxteam.nexus.core.configuration.ConfigurationManager;
import space.bxteam.nexus.core.feature.chat.ChatServiceImpl;
import space.bxteam.nexus.core.feature.ignore.IgnoreServiceImpl;
import space.bxteam.nexus.core.feature.jail.JailServiceImpl;
import space.bxteam.nexus.core.feature.randomteleport.RandomTeleportServiceImpl;
import space.bxteam.nexus.core.feature.spawn.SpawnServiceImpl;
import space.bxteam.nexus.core.feature.teleport.TeleportTaskService;
import space.bxteam.nexus.core.feature.teleportrequest.TeleportRequestServiceImpl;
import space.bxteam.nexus.core.updater.UpdateService;
import space.bxteam.nexus.core.feature.home.HomeServiceImpl;
import space.bxteam.nexus.core.feature.teleport.TeleportServiceImpl;
import space.bxteam.nexus.core.feature.warp.WarpServiceImpl;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.integration.placeholderapi.resolver.PlaceholderRegistry;
import space.bxteam.nexus.core.integration.placeholderapi.resolver.PlaceholderRegistryImpl;
import space.bxteam.nexus.feature.chat.ChatService;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.ignore.IgnoreService;
import space.bxteam.nexus.feature.jail.JailService;
import space.bxteam.nexus.feature.randomteleport.RandomTeleportService;
import space.bxteam.nexus.feature.spawn.SpawnService;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;
import space.bxteam.nexus.feature.warp.WarpService;

import java.nio.file.Path;

@RequiredArgsConstructor
public class NexusModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;
    private final Plugin plugin;
    private final ConfigurationManager configurationManager;

    @Override
    protected void configure() {
        this.bind(Plugin.class).toInstance(this.plugin);
        this.bind(PluginConfigurationProvider.class).toInstance(this.configurationProvider);
        this.bind(ConfigurationManager.class).toInstance(this.configurationManager);
        this.bind(Server.class).toInstance(this.plugin.getServer());
        this.bind(PluginManager.class).toInstance(this.plugin.getServer().getPluginManager());
        this.bind(PluginDescriptionFile.class).toInstance(this.plugin.getDescription());
        this.bind(ServicesManager.class).toInstance(this.plugin.getServer().getServicesManager());
        this.bind(Path.class)
                .annotatedWith(Names.named("dataFolder"))
                .toInstance(this.plugin.getDataFolder().toPath());
        this.bind(AudienceProvider.class).toInstance(BukkitAudiences.create(this.plugin));
        this.bind(PlaceholderRegistry.class).to(PlaceholderRegistryImpl.class).in(Singleton.class);
        this.bind(UpdateService.class).asEagerSingleton();

        this.bind(MiniMessage.class).toInstance(MiniMessage.miniMessage());
        this.bind(MiniMessage.class)
                .annotatedWith(Names.named("colorMiniMessage"))
                .toInstance(MiniMessage.builder()
                        .postProcessor(new AdventureUrlPostProcessor())
                        .postProcessor(new AdventureLegacyColorPostProcessor())
                        .preProcessor(new AdventureLegacyColorPreProcessor())
                        .build());

        this.bind(ChatService.class).to(ChatServiceImpl.class);
        this.bind(WarpService.class).to(WarpServiceImpl.class);
        this.bind(HomeService.class).to(HomeServiceImpl.class);
        this.bind(IgnoreService.class).to(IgnoreServiceImpl.class);
        this.bind(JailService.class).to(JailServiceImpl.class);
        this.bind(RandomTeleportService.class).to(RandomTeleportServiceImpl.class);
        this.bind(SpawnService.class).to(SpawnServiceImpl.class);
        this.bind(TeleportService.class).to(TeleportServiceImpl.class);
        this.bind(TeleportTaskService.class).asEagerSingleton();
        this.bind(TeleportRequestService.class).to(TeleportRequestServiceImpl.class);
    }
}
