package space.bxteam.nexus.feature.jail;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class JailPlayer {
    private final UUID player;
    private final Instant detainedAt;
    private final Duration prisonTime;
    private final String detainedBy;

    public JailPlayer(UUID player, Instant detainedAt, Duration prisonTime, String lockedUpBy) {
        this.player = player;
        this.detainedAt = detainedAt;
        this.prisonTime = prisonTime;
        this.detainedBy = lockedUpBy;
    }

    public UUID getPlayerUniqueId() {
        return this.player;
    }

    public Instant getDetainedAt() {
        return this.detainedAt;
    }

    public Duration getPrisonTime() {
        return this.prisonTime;
    }

    public String getDetainedBy() {
        return this.detainedBy;
    }

    public boolean isPrisonExpired() {
        return this.detainedAt.plus(this.prisonTime).isBefore(Instant.now());
    }

    public Instant getReleaseTime() {
        return Instant.now().plus(this.prisonTime);
    }

    public Duration getRemainingTime() {
        return Duration.between(Instant.now(), this.detainedAt.plus(this.prisonTime));
    }
}
