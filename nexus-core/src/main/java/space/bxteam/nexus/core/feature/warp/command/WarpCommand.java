package space.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
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
    void executeWarp(@Context Player player, @Arg Warp warp) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        player.teleport(warp.location());
        this.messageManager.create()
                .player(player)
                .sound(Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1)
                .send();
    }

    @Execute(name = "warp")
    @Permission("nexus.warp.other")
    void executeWarpOther(@Context Player player, @Arg Warp warp, @Arg Player target) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        target.teleport(warp.location());
    }

    @Execute(name = "setwarp")
    @Permission("nexus.setwarp")
    void executeSetWarp(@Context Player player, @Arg String name) {
        if (this.warpService.warpExists(name)) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.warp().warpAlreadyExists())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.warpService.createWarp(name, player.getLocation());
        this.messageManager.create()
                .player(player)
                .message(translation -> translation.warp().create())
                .placeholder("{WARP}", name)
                .send();
    }

    @Execute(name = "delwarp")
    @Permission("nexus.delwarp")
    void executeDelWarp(@Context Player player, @Arg Warp warp) {
        String name = warp.name();

        if (!this.warpService.warpExists(name)) {
            this.messageManager.create()
                    .player(player)
                    .message(translation -> translation.warp().notExist())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.warpService.removeWarp(name);
        this.messageManager.create()
                .player(player)
                .message(translation -> translation.warp().remove())
                .placeholder("{WARP}", name)
                .send();
    }
}
