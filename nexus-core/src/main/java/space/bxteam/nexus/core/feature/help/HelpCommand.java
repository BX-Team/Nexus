package space.bxteam.nexus.core.feature.help;

import com.eternalcode.multification.notice.NoticeBroadcast;
import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "help", aliases = {"report"})
@Permission("nexus.help")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HelpCommand {
    private final MultificationManager multificationManager;
    private final Server server;

    @Execute
    @CommandDocs(description = "Sends a help message to all online staff members with nexus.help.spy permission.", arguments = "<message>")
    void execute(@Context Player sender, @Join String message) {
        NoticeBroadcast notice = this.multificationManager.create()
                .console()
                .notice(translation -> translation.help().helpMessageSpy())
                .placeholder("{PLAYER}", sender.getName())
                .placeholder("{MESSAGE}", message);

        for (Player staff : this.server.getOnlinePlayers()) {
            if (!staff.hasPermission("nexus.help.spy")) {
                continue;
            }

            notice = notice.player(staff.getUniqueId());
        }

        notice.send();

        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.help().helpMessageSend())
                .send();
    }
}
