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
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleport.TeleportService;

import java.util.Optional;

@Command(name = "back")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportBackCommand {
    private final TeleportService teleportService;
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.teleport.back")
    @CommandDocs(description = "Teleport to the last location.")
    public void execute(@Context Player player) {
        Optional<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleport().lastLocationNoExist())
                    .send();
            return;
        }

        this.teleportService.teleport(player, location.get());
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToLastLocation())
                .send();
    }

    @Execute
    @Permission("nexus.teleport.back.other")
    @CommandDocs(description = "Teleport another player to the last location.", arguments = "<player>")
    public void execute(@Context CommandSender sender, @Arg Player target) {
        Optional<Location> location = this.teleportService.getLastLocation(target.getUniqueId());

        if (location.isEmpty()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.teleport().lastLocationNoExist())
                    .send();
            return;
        }

        this.teleportService.teleport(target, location.get());
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.teleport().teleportedToLastLocation())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.teleport().teleportedSpecifiedPlayerLastLocation())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
