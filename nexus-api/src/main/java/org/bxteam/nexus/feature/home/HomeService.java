package org.bxteam.nexus.feature.home;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for home management.
 */
public interface HomeService {
    /**
     * Create a home for the player.
     *
     * @param player player to create a home
     * @param name name of the home
     * @param location location of the home
     */
    void createHome(UUID player, String name, Location location);

    /**
     * Delete a home for the player.
     *
     * @param player player to delete a home
     * @param name name of the home
     */
    void deleteHome(UUID player, String name);

    /**
     * Check if the player has a home with the specified name.
     *
     * @param player player to check
     * @param name name of the home
     * @return true if the player has a home with the specified name, otherwise false
     */
    boolean hasHome(UUID player, String name);

    /**
     * Check if the player has a home.
     *
     * @param player player to check
     * @param home home to check
     * @return true if the player has a home, otherwise false
     */
    boolean hasHome(UUID player, Home home);

    /**
     * Get all homes of the player.
     *
     * @param player player to get homes
     * @return all homes of the player
     */
    Collection<Home> getHomes(UUID player);

    /**
     * Get a home of the player by name.
     *
     * @param player player to get a home
     * @param name name of the home
     * @return home of the player by name
     */
    Optional<Home> getHome(UUID player, String name);

    /**
     * Get an amount of homes of the player.
     *
     * @param player player to get an amount of homes
     * @return an amount of homes of the player
     */
    int getAmountOfHomes(UUID player);

    /**
     * Get a limit of homes of the player.
     *
     * @param player player to get a limit of homes
     * @return a limit of homes of the player
     */
    int getHomeLimit(Player player);
}
