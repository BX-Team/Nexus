package space.bxteam.nexus.core.feature.essentials.playerinfo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "online")
@Permission("nexus.online")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class OnlinePlayerCountCommand {
    private final MultificationManager multificationManager;
    private final Server server;

    @Execute
    void execute(@Context CommandSender sender) {
        long visiblePlayerCount = this.server.getOnlinePlayers().stream().count();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().onlinePlayersCountMessage())
                .placeholder("{ONLINE}", String.valueOf(visiblePlayerCount))
                .send();
    }
}
