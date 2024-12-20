package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.commons.bukkit.position.PositionFactory;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.event.EventCaller;
import space.bxteam.nexus.core.feature.teleport.Teleport;
import space.bxteam.nexus.core.feature.teleport.TeleportTaskService;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;
import space.bxteam.nexus.feature.warp.event.WarpTeleportEvent;

import java.time.Duration;
import java.util.UUID;

@Command(name = "warp")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WarpCommand {
    private final MultificationManager multificationManager;
    private final WarpService warpService;
    private final EventCaller eventCaller;
    private final TeleportTaskService teleportTaskService;
    private final PluginConfigurationProvider configurationProvider;

    @Execute
    @Permission("nexus.warp")
    @CommandDocs(description = "Teleport to the warp.", arguments = "<warp>")
    void executeWarp(@Context Player player, @Arg Warp warp) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.teleport(player, warp);
    }

    @Execute
    @Permission("nexus.warp.other")
    @CommandDocs(description = "Teleport another player to the warp.", arguments = "<warp> <player>")
    void executeWarpOther(@Context Player player, @Arg Warp warp, @Arg Player target) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.teleport(target, warp);
    }

    private void teleport(Player player, Warp warp) {
        Duration teleportTime = player.hasPermission("nexus.warp.instant")
                ? Duration.ZERO
                : this.configurationProvider.configuration().warp().timeToTeleport();

        Position from = PositionFactory.convert(player.getLocation());
        Position to = PositionFactory.convert(warp.location());
        UUID playerUniqueId = player.getUniqueId();

        WarpTeleportEvent event = new WarpTeleportEvent(player, warp);
        Teleport teleport = this.teleportTaskService.createTeleport(
                playerUniqueId,
                from,
                to,
                teleportTime
        );
        teleport.result().whenComplete((result, throwable) -> this.eventCaller.callEvent(event));
    }
}
