package org.bxteam.nexus.feature.jail;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a player that is jailed.
 */
public record JailPlayer(UUID playerUuid, Instant jailedAt, Duration prisonTime, String jailedBy) {
    public boolean isPrisonExpired() {
        return this.jailedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    public Instant getReleaseTime() {
        return Instant.now().plus(this.prisonTime);
    }

    public Duration getRemainingTime() {
        return Duration.between(Instant.now(), this.jailedAt.plus(this.prisonTime));
    }
}
