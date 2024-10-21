package space.bxteam.nexus.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

public interface WarpService {
    Warp createWarp(String name, Location location);

    void removeWarp(String warp);

    boolean warpExists(String name);

    Optional<Warp> getWarp(String name);

    Collection<String> getWarpNames();
}
