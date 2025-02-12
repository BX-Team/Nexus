package org.bxteam.nexus.feature.afk;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a player that is AFK.
 */
public record AfkPlayer(UUID playerUuid, AfkReason reason, Instant start) { }
