package space.bxteam.nexus.feature.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.UUID;

/**
 * Service for teleportation around the world.
 */
public interface TeleportService {
    /**
     * Teleport the player to the specified location.
     *
     * @param player The player to teleport
     * @param location The location to teleport the player to
     */
    void teleport(Player player, Location location);

    /**
     * Get the last location of the player before they were teleported.
     *
     * @param player The player to get the last location of
     */
    Optional<Location> getLastLocation(UUID player);

    /**
     * Mark the last location of the player before they were teleported.
     *
     * @param player The player to mark the last location of
     * @param location The location to mark as the last location
     * @apiNote This method is intended for internal use only.
     */
    @ApiStatus.Internal
    void markLastLocation(UUID player, Location location);
}
