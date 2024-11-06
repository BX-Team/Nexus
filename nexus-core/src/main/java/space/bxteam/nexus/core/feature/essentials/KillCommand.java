package space.bxteam.nexus.core.feature.essentials;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "kill")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class KillCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.kill")
    void execute(@Context Player player) {
        player.setHealth(0);

        this.messageManager.create()
                .player(player.getUniqueId())
                .message(translation -> translation.player().killedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();
    }

    @Execute
    @Permission("nexus.kill.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        target.setHealth(0);

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().killedMessage())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
