package space.bxteam.nexus.feature.warp;

import org.bukkit.Location;

/**
 * Represents a warp with the specified name and location.
 */
public interface Warp {
    Location location();

    String name();
}
