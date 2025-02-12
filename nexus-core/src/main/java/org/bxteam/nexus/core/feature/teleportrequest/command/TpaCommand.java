package org.bxteam.nexus.core.feature.teleportrequest.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.ignore.IgnoreService;
import org.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

@Command(name = "tpa")
@Permission("nexus.tpa")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TpaCommand {
    private final MultificationManager multificationManager;
    private final TeleportRequestService requestService;
    private final IgnoreService ignoreService;

    @Execute
    @CommandDocs(description = "Request teleportation to another player.", arguments = "<player>")
    void execute(@Context Player player, @Arg Player target) {
        if (player.equals(target)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaSelfMessage())
                    .send();
            return;
        }

        if (this.requestService.hasRequest(player.getUniqueId(), target.getUniqueId())) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaAlreadySentMessage())
                    .send();
            return;
        }

        this.ignoreService.isIgnored(target.getUniqueId(), player.getUniqueId()).thenAccept(ignored -> {
            if (ignored) {
                this.multificationManager.create()
                        .player(player.getUniqueId())
                        .notice(translation -> translation.teleportRequest().tpaIgnoredMessage())
                        .send();
                return;
            }

            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaSentMessage())
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            this.multificationManager.create()
                    .player(target.getUniqueId())
                    .notice(translation -> translation.teleportRequest().tpaReceivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();

            this.requestService.createRequest(player.getUniqueId(), target.getUniqueId());
        });
    }
}
