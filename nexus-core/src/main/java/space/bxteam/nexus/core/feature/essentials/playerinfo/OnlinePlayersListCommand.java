package space.bxteam.nexus.core.feature.essentials.playerinfo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

import java.util.Collection;

@Command(name = "list")
@Permission("nexus.list")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OnlinePlayersListCommand {
    private final MessageManager messageManager;
    private final Server server;

    @Execute
    void execute(@Context CommandSender sender) {
        Collection<? extends Player> online = this.server.getOnlinePlayers()
                .stream()
                .toList();

        String onlineCount = String.valueOf(online.size());
        String players = online.stream()
                .map(HumanEntity::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No players online");

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().onlinePlayersMessage())
                .placeholder("{ONLINE}", onlineCount)
                .placeholder("{PLAYERS}", players)
                .send();
    }
}
