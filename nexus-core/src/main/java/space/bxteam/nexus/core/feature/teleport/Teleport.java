package space.bxteam.nexus.core.feature.teleport;

import lombok.Getter;
import space.bxteam.commons.bukkit.position.Position;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class Teleport {
    private final UUID playerUniqueId;
    private final Position from;
    private final Position to;
    private final Instant teleportMoment;
    private final CompletableFuture<TeleportResult> result;

    public Teleport(UUID playerUniqueId, Position from, Position to, TemporalAmount time) {
        this.playerUniqueId = playerUniqueId;
        this.from = from;
        this.to = to;
        this.teleportMoment = Instant.now().plus(time);

        this.result = new CompletableFuture<>();
    }

    public void completeResult(TeleportResult result) {
        if (this.result.isDone()) {
            throw new IllegalStateException("Teleport result already completed");
        }

        this.result.complete(result);
    }
}
