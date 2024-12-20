package space.bxteam.nexus.core.feature.essentials.playerinfo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "ping")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PingCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.ping")
    @CommandDocs(description = "Check your ping.")
    void execute(@Context Player player) {
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.player().pingMessage())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .send();
    }

    @Execute
    @Permission("nexus.ping.other")
    @CommandDocs(description = "Check the ping of another player.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().pingOtherMessage())
                .placeholder("{PING}", String.valueOf(target.getPing()))
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
