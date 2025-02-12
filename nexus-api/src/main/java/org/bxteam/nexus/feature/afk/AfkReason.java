package org.bxteam.nexus.feature.afk;

/**
 * Represents the reason why a player is AFK.
 */
public enum AfkReason {
    /**
     * This reason is used when the player is AFK due to inactivity.
     */
    INACTIVITY,

    /**
     * This reason is used when the player AFK mode was toggled manually by a command.
     */
    COMMAND,

    /**
     * This reason is used when the player is AFK due to another reason, e.g. plugin API.
     * @apiNote This reason must be used only by 3rd party plugins that integrate with the AFK feature, not by the core.
     */
    OTHER
}
