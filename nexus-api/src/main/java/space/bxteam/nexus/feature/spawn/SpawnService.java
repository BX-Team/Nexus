package space.bxteam.nexus.feature.spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * This service provides methods to handle custom spawn location in the game.
 */
public interface SpawnService {
    /**
     * Teleports the player to the spawn location.
     *
     * @param player The player to teleport to the spawn location.
     */
    void teleportToSpawn(Player player);

    /**
     * Set the spawn location in the game.
     */
    void setSpawnLocation(Location location);

    /**
     * Provides the spawn location in the game.
     */
    Location getSpawnLocation();
}
