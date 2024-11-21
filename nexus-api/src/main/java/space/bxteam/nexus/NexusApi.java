package space.bxteam.nexus;

import space.bxteam.nexus.feature.chat.ChatService;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.warp.WarpService;

public interface NexusApi {
    ChatService getChatService();

    HomeService getHomeService();

    TeleportService getTeleportService();

    WarpService getWarpService();
}
