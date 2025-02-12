package org.bxteam.nexus.feature.warp;

import org.bukkit.Location;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for managing warps.
 */
public interface WarpService {
    /**
     * Create a new warp.
     *
     * @param name the name of the warp
     * @param location the location of the warp
     */
    void createWarp(String name, Location location);

    /**
     * Remove a warp.
     *
     * @param warp the name of the warp
     */
    void removeWarp(String warp);

    /**
     * Check if a warp exists.
     *
     * @param name the name of the warp
     * @return true if the warp exists
     */
    boolean warpExists(String name);

    /**
     * Get a warp.
     *
     * @param name the name of the warp
     * @return the warp
     */
    Optional<Warp> getWarp(String name);

    /**
     * Get all warp names.
     *
     * @return all warp names
     */
    Collection<String> getWarpNames();
}
