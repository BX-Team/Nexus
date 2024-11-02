package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

@RootCommand
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WarpCommand {
    private final MessageManager messageManager;
    private final WarpService warpService;

    @Execute(name = "warp")
    @Permission("nexus.warp")
    void executeWarp(@Context Player player, @Arg String name) {
        if (!this.warpService.warpExists(name)) {
        }

        Warp warp = this.warpService.getWarp(name).orElseThrow();

        player.teleport(warp.getLocation());
    }

    @Execute(name = "warp")
    @Permission("nexus.warp.other")
    void executeWarpOther(@Context Player player, @Arg String name, @Arg Player target) {
        if (!this.warpService.warpExists(name)) {
            return;
        }

        Warp warp = this.warpService.getWarp(name).orElseThrow();

        target.teleport(warp.getLocation());
    }

    @Execute(name = "setwarp")
    @Permission("nexus.setwarp")
    void executeSetWarp(@Context Player player, @Arg String name) {
        if (this.warpService.warpExists(name)) {
            return;
        }

        Warp createdWarp = this.warpService.createWarp(name, player.getLocation());
    }

    @Execute(name = "delwarp")
    @Permission("nexus.delwarp")
    void executeDelWarp(@Context Player player, @Arg String name) {
        if (!this.warpService.warpExists(name)) {
            return;
        }

        this.warpService.removeWarp(name);
    }
}
