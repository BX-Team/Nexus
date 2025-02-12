package org.bxteam.nexus;

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

/**
 * Main API for the Nexus plugin. Provides access to all services.
 */
public interface NexusApi {
    AfkService getAfkService();

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
