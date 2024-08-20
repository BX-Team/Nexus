package space.bxteam.nexus.core;

import space.bxteam.nexus.NexusApi;
import space.bxteam.nexus.core.feature.warp.WarpServiceImpl;
import space.bxteam.nexus.feature.warp.WarpService;

public class NexusApiImpl implements NexusApi {
    private final WarpService warpService = new WarpServiceImpl();

    @Override
    public WarpService getWarpService() {
        return warpService;
    }
}
