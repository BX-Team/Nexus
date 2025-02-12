package org.bxteam.nexus.core.feature.teleport.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.teleport.TeleportService;

@Command(name = "tpup", aliases = {"up", "teleportup"})
@Permission("nexus.tpup")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportUpCommand {
    private final TeleportService teleportService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Teleport to the highest block.")
    void execute(@Context Player player) {
        this.teleportToHighestBlock(player);
    }

    private void teleportToHighestBlock(Player player) {
        Location playerLocation = player.getLocation();
        int highestBlockYAt = player.getWorld().getHighestBlockYAt(playerLocation);

        Location newLocation = playerLocation.getBlock().getLocation();
        newLocation.setY(highestBlockYAt);
        newLocation.add(.5, 1.0, .5);
        newLocation.setPitch(playerLocation.getPitch());
        newLocation.setYaw(playerLocation.getYaw());

        this.teleportService.teleport(player, newLocation);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToHighestBlock())
                .placeholder("{Y}", String.valueOf(Math.round(newLocation.getY())))
                .send();
    }
}
