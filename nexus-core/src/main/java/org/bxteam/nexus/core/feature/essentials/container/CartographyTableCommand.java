package org.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;

@Command(name = "cartography", aliases = "cartography-table")
public class CartographyTableCommand {
    @Execute
    @Permission("nexus.cartography")
    @CommandDocs(description = "Opens a cartography table.")
    void executeSelf(@Context Player sender) {
        sender.openCartographyTable(null, true);
    }

    @Execute
    @Permission("nexus.cartography.other")
    @CommandDocs(description = "Opens a cartography table for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openCartographyTable(null, true);
    }
}
