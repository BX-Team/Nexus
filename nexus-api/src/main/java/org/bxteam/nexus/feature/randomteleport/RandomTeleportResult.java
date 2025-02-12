package org.bxteam.nexus.feature.randomteleport;

import org.bukkit.Location;

/**
 * Result of a random teleportation.
 */
public record RandomTeleportResult(boolean success, Location location) { }
