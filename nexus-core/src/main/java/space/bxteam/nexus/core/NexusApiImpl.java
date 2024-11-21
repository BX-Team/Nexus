package space.bxteam.nexus.core;

import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.NexusApi;
import space.bxteam.nexus.feature.chat.ChatService;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.warp.WarpService;

@RequiredArgsConstructor
public class NexusApiImpl implements NexusApi {
    private final Injector injector;

    @Override
    public ChatService getChatService() {
        return this.injector.getInstance(ChatService.class);
    }

    @Override
    public HomeService getHomeService() {
        return this.injector.getInstance(HomeService.class);
    }

    @Override
    public TeleportService getTeleportService() {
        return this.injector.getInstance(TeleportService.class);
    }

    @Override
    public WarpService getWarpService() {
        return this.injector.getInstance(WarpService.class);
    }
}
