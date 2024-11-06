package space.bxteam.nexus.core.feature.essentials.gamemode;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "gamemode", aliases = "gm")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GamemodeCommand {
    private final MessageManager messageManager;

    @Execute
    void execute(@Context Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.messageManager.create()
                .player(sender)
                .message(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .send();
    }

    @Execute
    void execute(@Context CommandSender sender, @Arg GameMode gameMode, @Arg Player target) {
        target.setGameMode(gameMode);

        this.messageManager.create()
                .player(target)
                .message(translation -> translation.player().gameModeMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .send();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().gameModeSetMessage())
                .placeholder("{GAMEMODE}", gameMode.name())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
