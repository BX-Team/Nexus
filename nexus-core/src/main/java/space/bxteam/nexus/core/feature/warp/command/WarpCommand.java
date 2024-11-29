package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

@Command(name = "warp")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WarpCommand {
    private final MultificationManager multificationManager;
    private final WarpService warpService;

    @Execute
    @Permission("nexus.warp")
    void executeWarp(@Context Player player, @Arg Warp warp) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        player.teleport(warp.location());
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().warpTeleported())
                .placeholder("{WARP}", name)
                .send();
    }

    @Execute
    @Permission("nexus.warp.other")
    void executeWarpOther(@Context Player player, @Arg Warp warp, @Arg Player target) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        target.teleport(warp.location());
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.warp().warpTeleported())
                .placeholder("{WARP}", name)
                .send();
    }
}
