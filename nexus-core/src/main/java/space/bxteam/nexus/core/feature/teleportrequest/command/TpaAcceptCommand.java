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
import space.bxteam.nexus.feature.teleport.TeleportService;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

import java.util.List;
import java.util.UUID;

@Command(name = "tpaaccept", aliases = {"tpaccept", "tpyes"})
@Permission("nexus.tpaaccept")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TpaAcceptCommand {
    private final Server server;
    private final MultificationManager multificationManager;
    private final TeleportRequestService requestService;
    private final TeleportService teleportService;

    @Execute
    void execute(@Context Player player, @Arg(RequesterArgument.KEY) Player target) {
        this.teleportService.teleport(target, player.getLocation());
        this.requestService.removeRequest(target.getUniqueId());

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportRequest().tpaAcceptMessage())
                .placeholder("{PLAYER}", target.getName())
                .send();

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.teleportRequest().tpaAcceptReceivedMessage())
                .placeholder("{PLAYER}", player.getName())
                .send();
    }

    @Execute(name = "-all", aliases = {"*"})
    void executeAll(@Context Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaAcceptNoRequestMessage())
                    .send();
            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {
                this.teleportService.teleport(requester, player.getLocation());

                this.multificationManager.create()
                        .player(uniqueId)
                        .notice(translation -> translation.teleportRequest().tpaAcceptReceivedMessage())
                        .placeholder("{PLAYER}", player.getName())
                        .send();
            }
        }

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation ->  translation.teleportRequest().tpaAcceptAllAccepted())
                .send();
    }
}
