package space.bxteam.nexus;

import space.bxteam.nexus.feature.chat.ChatService;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.ignore.IgnoreService;
import space.bxteam.nexus.feature.jail.JailService;
import space.bxteam.nexus.feature.privatechat.PrivateChatService;
import space.bxteam.nexus.feature.randomteleport.RandomTeleportService;
import space.bxteam.nexus.feature.spawn.SpawnService;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;
import space.bxteam.nexus.feature.warp.WarpService;

/**
 * Main API for the Nexus plugin. Provides access to all services.
 */
public interface NexusApi {
    ChatService getChatService();

    HomeService getHomeService();

    IgnoreService getIgnoreService();

    JailService getJailService();

    PrivateChatService getPrivateChatService();

    RandomTeleportService getRandomTeleportService();

    SpawnService getSpawnService();

    TeleportService getTeleportService();

    TeleportRequestService getTeleportRequestService();

    WarpService getWarpService();
}
