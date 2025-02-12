package org.bxteam.nexus.feature.randomteleport;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

/**
 * Service for random teleportation.
 */
public interface RandomTeleportService {
    /**
     * Teleports the player to a random location in their current world.
     *
     * @param player the player to teleport
     * @return a CompletableFuture that completes with the result of the teleportation
     */
    CompletableFuture<RandomTeleportResult> teleport(Player player);

    /**
     * Teleports the player to a random location in the specified world.
     *
     * @param player the player to teleport
     * @param world the world to teleport the player to
     * @return a CompletableFuture that completes with the result of the teleportation
     */
    CompletableFuture<RandomTeleportResult> teleport(Player player, World world);

    /**
     * Finds a safe random location in the specified world.
     *
     * @param world the world to find a location in
     * @param attemptCount the number of attempts to find a safe location
     * @return a CompletableFuture that completes with the found location
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, int attemptCount);

    /**
     * Finds a safe random location in the specified world within a given radius.
     *
     * @param world the world to find a location in
     * @param radius the radius within which to find a location
     * @param attemptCount the number of attempts to find a safe location
     * @return a CompletableFuture that completes with the found location
     */
    CompletableFuture<Location> getSafeRandomLocation(World world, int radius, int attemptCount);

    /**
     * Finds a safe random location within the world border of the specified world.
     *
     * @param world the world to find a location in
     * @param attemptCount the number of attempts to find a safe location
     * @return a CompletableFuture that completes with the found location
     */
    CompletableFuture<Location> getSafeRandomLocationInWorldBorder(World world, int attemptCount);
}
