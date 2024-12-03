package space.bxteam.nexus;

import space.bxteam.nexus.feature.chat.ChatService;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.jail.JailService;
import space.bxteam.nexus.feature.randomteleport.RandomTeleportService;
import space.bxteam.nexus.feature.spawn.SpawnService;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;
import space.bxteam.nexus.feature.warp.WarpService;

public interface NexusApi {
    ChatService getChatService();

    HomeService getHomeService();

    JailService getJailService();

    RandomTeleportService getRandomTeleportService();

    SpawnService getSpawnService();

    TeleportService getTeleportService();

    TeleportRequestService getTeleportRequestService();

    WarpService getWarpService();
}
