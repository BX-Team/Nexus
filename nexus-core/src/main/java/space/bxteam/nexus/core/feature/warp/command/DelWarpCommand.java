package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

@Command(name = "delwarp")
@Permission("nexus.delwarp")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DelWarpCommand {
    private final MultificationManager multificationManager;
    private final WarpService warpService;

    @Execute
    @CommandDocs(description = "Delete a warp.", arguments = "<warp>")
    void executeDelWarp(@Context Player player, @Arg Warp warp) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.warpService.removeWarp(name);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().remove())
                .placeholder("{WARP}", name)
                .send();
    }
}
