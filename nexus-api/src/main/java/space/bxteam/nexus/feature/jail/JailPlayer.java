package space.bxteam.nexus.feature.jail;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a player that is jailed.
 */
public class JailPlayer {
    private final UUID player;
    private final Instant jailedAt;
    private final Duration prisonTime;
    private final String jailedBy;

    public JailPlayer(UUID player, Instant detainedAt, Duration prisonTime, String lockedUpBy) {
        this.player = player;
        this.jailedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.jailedBy = lockedUpBy;
    }

    public UUID getPlayerUniqueId() {
        return this.player;
    }

    public Instant getJailedAt() {
        return this.jailedAt;
    }

    public Duration getPrisonTime() {
        return this.prisonTime;
    }

    public String getJailedBy() {
        return this.jailedBy;
    }

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
