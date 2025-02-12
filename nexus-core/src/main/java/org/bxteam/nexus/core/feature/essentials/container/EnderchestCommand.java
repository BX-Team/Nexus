package org.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;

@Command(name = "enderchest", aliases = { "ec" })
public class EnderchestCommand {
    @Execute
    @Permission("nexus.enderchest")
    @CommandDocs(description = "Opens your enderchest.")
    void execute(@Context Player player) {
        player.openInventory(player.getEnderChest());
    }

    @Execute
    @Permission("nexus.enderchest.other")
    @CommandDocs(description = "Opens the enderchest of another player.", arguments = "<player>")
    void execute(@Context Player player, @Arg Player target) {
        player.openInventory(target.getEnderChest());
    }
}
