package space.bxteam.nexus;

import space.bxteam.nexus.feature.home.HomeService;
import space.bxteam.nexus.feature.warp.WarpService;

public interface NexusApi {
    HomeService getHomeService();

    WarpService getWarpService();
}
