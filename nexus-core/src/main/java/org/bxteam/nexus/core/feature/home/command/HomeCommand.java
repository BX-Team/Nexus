package org.bxteam.nexus.core.feature.home.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.commons.bukkit.position.Position;
import org.bxteam.commons.bukkit.position.PositionFactory;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import org.bxteam.nexus.core.event.EventCaller;
import org.bxteam.nexus.core.feature.teleport.Teleport;
import org.bxteam.nexus.core.feature.teleport.TeleportTaskService;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.home.Home;
import org.bxteam.nexus.feature.home.HomeService;
import org.bxteam.nexus.feature.home.event.HomeTeleportEvent;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Command(name = "home")
@Permission("nexus.home")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HomeCommand {
    private final MultificationManager multificationManager;
    private final HomeService homeService;
    private final PluginConfigurationProvider pluginConfiguration;
    private final EventCaller eventCaller;
    private final TeleportTaskService teleportTaskService;

    @Execute
    @CommandDocs(description = "Teleports to your first home.")
    void home(@Context Player player) {
        Collection<Home> playerHomes = this.homeService.getHomes(player.getUniqueId());

        if (playerHomes.isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().noHomes())
                    .send();
            return;
        }

        if (playerHomes.size() > 1) {
            String homes = String.join(
                    ", ",
                    playerHomes.stream()
                            .map(Home::name)
                            .toList());

            Optional<Home> mainHome = playerHomes.stream()
                    .filter(home -> home.name().equals(this.pluginConfiguration.configuration().homes().defaultHomeName()))
                    .findFirst();

            if (mainHome.isPresent()) {
                this.teleport(player, mainHome.get());
                return;
            }

            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.home().homeList())
                    .placeholder("{HOMES}", homes)
                    .send();
            return;
        }

        Home firstHome = playerHomes.iterator().next();

        this.teleport(player, firstHome);
    }

    @Execute
    @CommandDocs(description = "Teleports to a specific home.", arguments = "<home>")
    void home(@Context Player player, @Arg Home home) {
        this.teleport(player, home);
    }

    private void teleport(Player player, Home home) {
        UUID playerUniqueId = player.getUniqueId();

        Duration teleportTime = player.hasPermission("nexus.home.instant")
                ? Duration.ZERO
                : this.pluginConfiguration.configuration().homes().timeToTeleport();

        Position from = PositionFactory.convert(player.getLocation());
        Position to = PositionFactory.convert(home.location());

        HomeTeleportEvent event = new HomeTeleportEvent(playerUniqueId, home);
        Teleport teleport = this.teleportTaskService.createTeleport(
                playerUniqueId,
                from,
                to,
                teleportTime
        );
        teleport.result().whenComplete((result, throwable) -> this.eventCaller.callEvent(event));
    }
}
