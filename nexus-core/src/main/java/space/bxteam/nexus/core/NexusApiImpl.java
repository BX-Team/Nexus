package space.bxteam.nexus.core;

import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import space.bxteam.nexus.NexusApi;
import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.warp.WarpService;

@RequiredArgsConstructor
public class NexusApiImpl implements NexusApi {
    private final Injector injector;

    @Override
    public HomeService getHomeService() {
        return this.injector.getInstance(HomeService.class);
    }

    @Override
    public WarpService getWarpService() {
        return this.injector.getInstance(WarpService.class);
    }
}
