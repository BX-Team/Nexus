package space.bxteam.nexus.core.feature.teleportrequest.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

@Command(name = "tpa")
@Permission("nexus.tpa")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TpaCommand {
    private final MultificationManager multificationManager;
    private final TeleportRequestService requestService;

    @Execute
    public void execute(@Context Player player, @Arg Player target) {
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
    }
}
