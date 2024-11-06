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
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "ping")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PingCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.ping")
    void execute(@Context Player player) {
        this.messageManager.create()
                .player(player)
                .message(translation -> translation.player().pingMessage())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .send();
    }

    @Execute
    @Permission("nexus.ping.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().pingOtherMessage())
                .placeholder("{PING}", String.valueOf(target.getPing()))
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
