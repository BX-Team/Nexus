package org.bxteam.nexus.feature.jail;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

/**
 * Service for jail management.
 */
public interface JailService {
    /**
     * Detains the player in jail. If the duration is null, the default duration from plugin configuration is used.
     * If the player is already jailed, the duration is updated. Detained player is teleported to jail.
     * Returns true if player has been detained.
     *
     * @param jailName   The name of the jail area.
     * @param player     The player to detain.
     * @param detainedBy The player who detained the player.
     * @param duration   The duration of the detainment.
     */
    void jailPlayer(String jailName, Player player, CommandSender detainedBy, @Nullable Duration duration);

    /**
     * Releases the player from jail. If the player is not jailed, nothing happens.
     * Released player is teleported to spawn.
     * Returns true if player has been released.
     *
     * @param player The player to release.
     */
    void releasePlayer(Player player);

    /**
     * Releases all players from jail and teleports them to spawn.
     * If some players are offline and there are still jailed, they will not be teleported to spawn.
     */
    void releaseAllPlayers();

    /**
     * Checks if the player is jailed.
     *
     * @param player The player to get.
     * @return True if the player is jailed.
     */
    boolean isPlayerJailed(UUID player);

    /**
     * Gets all jailed players.
     */
    Collection<JailPlayer> getJailedPlayers();

    /**
     * Creates a new jail area with specified name.
     *
     * @param name The name of the jail area.
     * @param jailLocation The location of the jail.
     */
    void createJailLocation(String name, Location jailLocation);

    /**
     * Removes the jail area with specified name.
     *
     * @param name The name of the jail area.
     */
    void removeJailArea(String name);

    /**
     * Checks if jail with specified name exists.
     *
     * @param name The name of the jail.
     * @return True if jail exists.
     */
    boolean jailExists(String name);

    /**
     * Gets all jail names.
     */
    Collection<String> getAllJailNames();
}
