package space.bxteam.nexus.core.feature.teleport;

import com.eternalcode.multification.notice.Notice;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.scanner.annotations.component.Task;
import space.bxteam.nexus.core.utils.DurationUtil;
import space.bxteam.nexus.feature.teleport.TeleportService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Task(delay = 1L, period = 20L)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportTask implements Runnable {
    private static final int SECONDS_OFFSET = 1;

    private final MultificationManager multificationManager;
    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;
    private final Server server;

    @Override
    public void run() {
        List<Teleport> teleports = List.copyOf(this.teleportTaskService.getTeleports());

        for (Teleport teleport : teleports) {
            UUID uuid = teleport.playerUniqueId();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                this.teleportTaskService.removeTeleport(uuid);
                continue;
            }

            if (this.hasPlayerMovedDuringTeleport(player, teleport)) {
                this.teleportTaskService.removeTeleport(uuid);
                teleport.completeResult(TeleportResult.MOVED_DURING_TELEPORT);

                this.multificationManager.create()
                        .notice(translation -> Notice.actionbar(StringUtils.EMPTY))
                        .notice(translation -> translation.teleport().teleportTaskCanceled())
                        .player(player.getUniqueId())
                        .send();

                continue;
            }

            Instant now = Instant.now();
            Instant teleportMoment = teleport.teleportMoment();

            if (now.isBefore(teleportMoment)) {
                Duration duration = Duration.between(now, teleportMoment);

                this.multificationManager.create()
                        .notice(translation -> translation.teleport().teleporting())
                        .placeholder("{TIME}", DurationUtil.format(duration.plusSeconds(SECONDS_OFFSET), true))
                        .player(player.getUniqueId())
                        .send();
                continue;
            }

            this.completeTeleport(player, teleport);
        }
    }

    private void completeTeleport(Player player, Teleport teleport) {
        Location destinationLocation = PositionFactory.convert(teleport.to());
        UUID uuid = teleport.playerUniqueId();

        this.teleportService.teleport(player, destinationLocation);
        this.teleportTaskService.removeTeleport(uuid);

        teleport.completeResult(TeleportResult.SUCCESS);

        this.multificationManager.create()
                .notice(translation -> translation.teleport().teleported())
                .player(player.getUniqueId())
                .send();
    }

    private boolean hasPlayerMovedDuringTeleport(Player player, Teleport teleport) {
        Location startLocation = PositionFactory.convert(teleport.from());

        return player.getLocation().distance(startLocation) > 0.5;
    }
}
