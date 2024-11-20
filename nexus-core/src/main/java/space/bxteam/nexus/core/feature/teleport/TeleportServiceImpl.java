package space.bxteam.nexus.core.feature.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.lib.PaperLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.commons.position.Position;
import space.bxteam.nexus.commons.position.PositionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class TeleportServiceImpl implements TeleportService {
    private final Map<UUID, Position> lastPosition = new HashMap<>();

    @Override
    public void teleport(Player player, Location location) {
        Location lastLocation = player.getLocation().clone();
        PaperLib.teleportAsync(player, location);
        this.markLastLocation(player.getUniqueId(), lastLocation);
    }

    @Override
    public Optional<Location> getLastLocation(UUID player) {
        return Optional.ofNullable(this.lastPosition.get(player)).map(PositionFactory::convert);
    }

    @Override
    public void markLastLocation(UUID player, Location location) {
        this.lastPosition.put(player, PositionFactory.convert(location));
    }
}
