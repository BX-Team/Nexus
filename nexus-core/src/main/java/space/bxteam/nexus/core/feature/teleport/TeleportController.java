package space.bxteam.nexus.core.feature.teleport;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.bxteam.nexus.core.registration.annotations.component.Controller;
import space.bxteam.nexus.feature.teleport.TeleportService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportController implements Listener {
    private final TeleportService teleportService;
    private final TeleportTaskService teleportTaskService;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        this.teleportService.markLastLocation(player.getUniqueId(), player.getLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            UUID uuid = player.getUniqueId();

            if (this.teleportTaskService.isInTeleport(uuid)) this.teleportTaskService.removeTeleport(uuid);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (this.teleportTaskService.isInTeleport(uuid)) this.teleportTaskService.removeTeleport(uuid);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (this.teleportTaskService.isInTeleport(uuid)) this.teleportTaskService.removeTeleport(uuid);
    }
}
