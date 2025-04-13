package org.bxteam.nexus.core.feature.warp.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.warp.WarpService;

@Command(name = "setwarp")
@Permission("nexus.setwarp")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SetWarpCommand {
    private final MultificationManager multificationManager;
    private final WarpService warpService;

    @Execute
    @CommandDocs(description = "Set a warp.", arguments = "<name>")
    void executeSetWarp(@Context Player player, @Arg String name) {
        if (this.warpService.warpExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.warp().warpAlreadyExists())
                    .placeholder("{WARP}", name)
                    .send();
            return;
        }

        this.warpService.createWarp(name, player.getLocation());
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.warp().create())
                .placeholder("{WARP}", name)
                .send();
    }
}
