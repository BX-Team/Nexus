package org.bxteam.nexus.core.feature.gamemode;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "gamemode", aliases = "gm")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GamemodeCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.gamemode")
    @CommandDocs(description = "Change your gamemode.", arguments = "<gamemode>")
    void execute(@Context Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .send();
    }

    @Execute
    @Permission("nexus.gamemode.other")
    @CommandDocs(description = "Change the gamemode of another player.", arguments = "<gamemode> <player>")
    void execute(@Context CommandSender sender, @Arg GameMode gameMode, @Arg Player target) {
        target.setGameMode(gameMode);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().gameModeSetMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
