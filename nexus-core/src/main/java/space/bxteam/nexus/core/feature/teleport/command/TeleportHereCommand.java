package space.bxteam.nexus.core.feature.teleport.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleport.TeleportService;

@Command(name = "tphere", aliases = {"s"})
@Permission("nexus.tphere")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TeleportHereCommand {
    private final TeleportService teleportService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Teleport a player to you.", arguments = "<player>")
    void execute(@Context Player sender, @Arg Player target) {
        this.teleportService.teleport(target, sender.getLocation());
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.teleport().teleportedPlayerToPlayer())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{SENDER}", sender.getName())
                .send();
    }
}
