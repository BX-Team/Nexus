package org.bxteam.nexus.core;

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
import org.bxteam.commons.adventure.processor.AdventureLegacyColorPostProcessor;
import org.bxteam.commons.adventure.processor.AdventureLegacyColorPreProcessor;
import org.bxteam.commons.adventure.processor.AdventureUrlPostProcessor;
import org.bxteam.commons.logger.ExtendedLogger;
import org.bxteam.commons.updater.ModrinthVersionFetcher;
import org.bxteam.commons.updater.VersionFetcher;
import org.bxteam.nexus.core.configuration.ConfigurationManager;
import org.bxteam.nexus.core.feature.afk.AfkServiceImpl;
import org.bxteam.nexus.core.feature.chat.ChatServiceImpl;
import org.bxteam.nexus.core.feature.ignore.IgnoreServiceImpl;
import org.bxteam.nexus.core.feature.jail.JailServiceImpl;
import org.bxteam.nexus.core.feature.privatechat.PrivateChatServiceImpl;
import org.bxteam.nexus.core.feature.randomteleport.RandomTeleportServiceImpl;
import org.bxteam.nexus.core.feature.spawn.SpawnServiceImpl;
import org.bxteam.nexus.core.feature.teleport.TeleportTaskService;
import org.bxteam.nexus.core.feature.teleportrequest.TeleportRequestServiceImpl;
import org.bxteam.nexus.core.feature.home.HomeServiceImpl;
import org.bxteam.nexus.core.feature.teleport.TeleportServiceImpl;
import org.bxteam.nexus.core.feature.warp.WarpServiceImpl;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistry;
import org.bxteam.nexus.core.placeholder.PlaceholderRegistryImpl;
import org.bxteam.nexus.feature.afk.AfkService;
import org.bxteam.nexus.feature.chat.ChatService;
import org.bxteam.nexus.feature.home.HomeService;
import org.bxteam.nexus.feature.ignore.IgnoreService;
import org.bxteam.nexus.feature.jail.JailService;
import org.bxteam.nexus.feature.privatechat.PrivateChatService;
import org.bxteam.nexus.feature.randomteleport.RandomTeleportService;
import org.bxteam.nexus.feature.spawn.SpawnService;
import org.bxteam.nexus.feature.teleport.TeleportService;
import org.bxteam.nexus.feature.teleportrequest.TeleportRequestService;
import org.bxteam.nexus.feature.warp.WarpService;

import java.nio.file.Path;

@RequiredArgsConstructor
public class NexusModule extends AbstractModule {
    private final PluginConfigurationProvider configurationProvider;
    private final Plugin plugin;
    private final ConfigurationManager configurationManager;
    private final ExtendedLogger logger;

    @Override
    protected void configure() {
        bindCoreComponents();
        bindServices();
        bindMiniMessage();
    }

    private void bindCoreComponents() {
        this.bind(Plugin.class).toInstance(plugin);
        this.bind(PluginConfigurationProvider.class).toInstance(configurationProvider);
        this.bind(ConfigurationManager.class).toInstance(configurationManager);
        this.bind(ExtendedLogger.class).toInstance(logger);
        this.bind(VersionFetcher.class).toInstance(new ModrinthVersionFetcher("nexuss"));
        this.bind(Server.class).toInstance(plugin.getServer());
        this.bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
        this.bind(PluginDescriptionFile.class).toInstance(plugin.getDescription());
        this.bind(ServicesManager.class).toInstance(plugin.getServer().getServicesManager());
        this.bind(Path.class).annotatedWith(Names.named("dataFolder")).toInstance(plugin.getDataFolder().toPath());
        this.bind(AudienceProvider.class).toInstance(BukkitAudiences.create(plugin));
        this.bind(PlaceholderRegistry.class).to(PlaceholderRegistryImpl.class).in(Singleton.class);
    }

    private void bindServices() {
        this.bind(AfkService.class).to(AfkServiceImpl.class);
        this.bind(ChatService.class).to(ChatServiceImpl.class);
        this.bind(WarpService.class).to(WarpServiceImpl.class);
        this.bind(HomeService.class).to(HomeServiceImpl.class);
        this.bind(IgnoreService.class).to(IgnoreServiceImpl.class);
        this.bind(JailService.class).to(JailServiceImpl.class);
        this.bind(PrivateChatService.class).to(PrivateChatServiceImpl.class);
        this.bind(RandomTeleportService.class).to(RandomTeleportServiceImpl.class);
        this.bind(SpawnService.class).to(SpawnServiceImpl.class);
        this.bind(TeleportService.class).to(TeleportServiceImpl.class);
        this.bind(TeleportTaskService.class).asEagerSingleton();
        this.bind(TeleportRequestService.class).to(TeleportRequestServiceImpl.class);
    }

    private void bindMiniMessage() {
        this.bind(MiniMessage.class).toInstance(MiniMessage.miniMessage());
        this.bind(MiniMessage.class)
                .annotatedWith(Names.named("colorMiniMessage"))
                .toInstance(MiniMessage.builder()
                        .postProcessor(new AdventureUrlPostProcessor())
                        .postProcessor(new AdventureLegacyColorPostProcessor())
                        .preProcessor(new AdventureLegacyColorPreProcessor())
                        .build());
    }
}
