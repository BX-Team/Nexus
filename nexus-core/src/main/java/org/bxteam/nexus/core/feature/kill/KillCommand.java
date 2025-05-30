package org.bxteam.nexus.core.feature.kill;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "kill")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class KillCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.kill")
    @CommandDocs(description = "Kills you.")
    void execute(@Context Player player) {
        player.setHealth(0);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.player().killedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();
    }

    @Execute
    @Permission("nexus.kill.other")
    @CommandDocs(description = "Kills another player.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        target.setHealth(0);

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().killedMessage())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
