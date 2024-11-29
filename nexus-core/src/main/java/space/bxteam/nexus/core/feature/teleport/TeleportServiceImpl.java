package space.bxteam.nexus.core.feature.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.lib.PaperLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.teleport.event.TeleportEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Singleton
public class TeleportServiceImpl implements TeleportService {
    private final Map<UUID, Position> lastPosition = new HashMap<>();
    private final EventCaller eventCaller;

    @Override
    public void teleport(Player player, Location location) {
        TeleportEvent event = this.eventCaller.callEvent(new TeleportEvent(player, location));

        if (event.isCancelled()) {
            return;
        }

        Location lastLocation = player.getLocation().clone();
        PaperLib.teleportAsync(player, event.getLocation());
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
