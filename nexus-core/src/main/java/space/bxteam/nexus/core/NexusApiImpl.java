package space.bxteam.nexus.core;

import com.google.inject.Injector;
import space.bxteam.nexus.NexusApi;
import space.bxteam.nexus.feature.warp.WarpService;

public class NexusApiImpl implements NexusApi {
    private final Injector injector;

    public NexusApiImpl(Injector injector) {
        this.injector = injector;
    }

    @Override
    public WarpService getWarpService() {
        return this.injector.getInstance(WarpService.class);
    }
}
