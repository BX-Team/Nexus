package org.bxteam.nexus.feature.afk;

import java.util.UUID;

/**
 * Service that provides AFK functionality.
 */
public interface AfkService {
    /**
     * Switches the AFK status of the player.
     *
     * @param playerUuid the UUID of the player
     * @param reason the reason for the AFK status
     */
    void switchAfk(UUID playerUuid, AfkReason reason);

    /**
     * Marks the player as AFK.
     *
     * @param playerUuid the UUID of the player
     * @param reason the reason for the AFK status
     */
    void markAfk(UUID playerUuid, AfkReason reason);

    /**
     * Unmarks the player as AFK.
     *
     * @param playerUuid the UUID of the player
     */
    void unmarkAfk(UUID playerUuid);

    /**
     * Marks the player as interacting.
     *
     * @param playerUuid the UUID of the player
     */
    void markInteraction(UUID playerUuid);

    /**
     * Checks if the player is AFK.
     *
     * @param playerUuid the UUID of the player
     * @return true if the player is AFK, false otherwise
     */
    boolean isAfk(UUID playerUuid);

    /**
     * Checks if the player is inactive.
     *
     * @param playerUuid the UUID of the player
     * @return true if the player is inactive, false otherwise
     */
    boolean isInactive(UUID playerUuid);
}
