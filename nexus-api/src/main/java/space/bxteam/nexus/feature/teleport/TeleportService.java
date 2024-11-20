package space.bxteam.nexus.feature.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.UUID;

public interface TeleportService {
    void teleport(Player player, Location location);

    Optional<Location> getLastLocation(UUID player);

    @ApiStatus.Internal
    void markLastLocation(UUID player, Location location);
}
