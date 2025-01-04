package space.bxteam.nexus.core;

import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.NexusApi;
import space.bxteam.nexus.feature.afk.AfkService;
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

@RequiredArgsConstructor
public class NexusApiImpl implements NexusApi {
    private final Injector injector;

    @Override
    public AfkService getAfkService() {
        return this.injector.getInstance(AfkService.class);
    }

    @Override
    public ChatService getChatService() {
        return this.injector.getInstance(ChatService.class);
    }

    @Override
    public HomeService getHomeService() {
        return this.injector.getInstance(HomeService.class);
    }

    @Override
    public IgnoreService getIgnoreService() {
        return this.injector.getInstance(IgnoreService.class);
    }

    @Override
    public JailService getJailService() {
        return this.injector.getInstance(JailService.class);
    }

    @Override
    public PrivateChatService getPrivateChatService() {
        return this.injector.getInstance(PrivateChatService.class);
    }

    @Override
    public RandomTeleportService getRandomTeleportService() {
        return this.injector.getInstance(RandomTeleportService.class);
    }

    @Override
    public SpawnService getSpawnService() {
        return this.injector.getInstance(SpawnService.class);
    }

    @Override
    public TeleportService getTeleportService() {
        return this.injector.getInstance(TeleportService.class);
    }

    @Override
    public TeleportRequestService getTeleportRequestService() {
        return this.injector.getInstance(TeleportRequestService.class);
    }

    @Override
    public WarpService getWarpService() {
        return this.injector.getInstance(WarpService.class);
    }
}
