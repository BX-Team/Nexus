package space.bxteam.nexus.core.feature.adminchat;

import com.eternalcode.multification.notice.NoticeBroadcast;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "adminchat", aliases = {"ac", "achat"})
@Permission("nexus.adminchat")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AdminChatCommand {
    private final Server server;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Send a message to all staff members with nexus.adminchat.spy permission.", arguments = "<message>")
    void execute(@Context CommandSender sender, @Join String message) {
        NoticeBroadcast notice = this.multificationManager.create()
                .console()
                .notice(translation -> translation.adminChat().message())
                .placeholder("{PLAYER}", sender.getName())
                .placeholder("{MESSAGE}", message);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("nexus.adminchat.spy")) {
                continue;
            }

            notice = notice.player(player.getUniqueId());
        }

        notice.send();
    }
}
