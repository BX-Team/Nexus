package space.bxteam.nexus.core.feature.essentials.playerinfo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "online")
@Permission("nexus.online")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OnlinePlayerCountCommand {
    private final MessageManager messageManager;
    private final Server server;

    @Execute
    void execute(@Context CommandSender sender) {
        long visiblePlayerCount = this.server.getOnlinePlayers().stream().count();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().onlinePlayersCountMessage())
                .placeholder("{ONLINE}", String.valueOf(visiblePlayerCount))
                .send();
    }
}
