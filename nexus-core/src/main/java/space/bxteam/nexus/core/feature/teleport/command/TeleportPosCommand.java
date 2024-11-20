package space.bxteam.nexus.core.feature.teleport.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleport.TeleportService;

@Command(name = "tppos")
@Permission("nexus.tppos")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportPosCommand {
    private final TeleportService teleportService;
    private final MultificationManager multificationManager;

    @Execute
    void execute(@Context Player player, @Arg Location location) {
        this.teleport(player, location);
    }

    @Execute
    void execute(@Context CommandSender sender, @Arg Location location, @Arg Player target) {
        this.teleport(target, location);

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{X}", String.valueOf(location.getBlockX()))
                .placeholder("{Y}", String.valueOf(location.getBlockY()))
                .placeholder("{Z}", String.valueOf(location.getBlockZ()))
                .send();
    }

    private void teleport(Player player, Location location) {
        location.setWorld(player.getWorld());
        this.teleportService.teleport(player, location);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToCoordinates())
                .placeholder("{X}", String.valueOf(location.getBlockX()))
                .placeholder("{Y}", String.valueOf(location.getBlockY()))
                .placeholder("{Z}", String.valueOf(location.getBlockZ()))
                .send();
    }
}
