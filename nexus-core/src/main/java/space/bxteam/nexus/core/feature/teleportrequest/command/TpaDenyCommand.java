package space.bxteam.nexus.core.feature.teleportrequest.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

import java.util.List;
import java.util.UUID;

@Command(name = "tpadeny", aliases = {"tpno", "tpdeny"})
@Permission({"nexus.tpadeny"})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TpaDenyCommand {
    private final Server server;
    private final MultificationManager multificationManager;
    private final TeleportRequestService requestService;

    @Execute
    void execute(@Context Player player, @Arg(RequesterArgument.KEY) Player target) {
        this.requestService.removeRequest(target.getUniqueId());

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportRequest().tpaDenyMessage())
                .placeholder("{PLAYER}", target.getName())
                .send();

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.teleportRequest().tpaDenyReceivedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();
    }

    @Execute(name = "-all", aliases = {"*"})
    void executeAll(@Context Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaDenyNoRequestMessage())
                    .send();
            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {
                this.multificationManager.create()
                        .player(uniqueId)
                        .notice(translation -> translation.teleportRequest().tpaDenyReceivedMessage())
                        .placeholder("{PLAYER}", player.getName())
                        .send();
            }
        }

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportRequest().tpaDenyAllDenied())
                .send();
    }
}
