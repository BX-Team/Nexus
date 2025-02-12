package org.bxteam.nexus.core.feature.teleport;

import com.google.inject.Singleton;
import org.bxteam.commons.bukkit.position.Position;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.*;

@Singleton
public class TeleportTaskService {
    private final Map<UUID, Teleport> teleports = new HashMap<>();

    public Teleport createTeleport(UUID uuid, Position from, Position to, TemporalAmount time) {
        Teleport teleport = new Teleport(uuid, from, to, time);
        teleports.put(uuid, teleport);
        return teleport;
    }

    public boolean isInTeleport(UUID uuid) {
        return this.getTeleport(uuid)
                .filter(teleport -> Instant.now().isBefore(teleport.teleportMoment()))
                .map(teleport -> true)
                .orElseGet(() -> {
                    removeTeleport(uuid);
                    return false;
                });
    }

    public void removeTeleport(UUID uuid) {
        teleports.remove(uuid);
    }

    public Optional<Teleport> getTeleport(UUID uuid) {
        return Optional.ofNullable(teleports.get(uuid));
    }

    public Collection<Teleport> getTeleports() {
        return Collections.unmodifiableCollection(teleports.values());
    }
}