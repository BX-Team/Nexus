package org.bxteam.nexus.core.feature.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;

@Command(name = "smithingtable", aliases = "smithing-table")
public class SmithingTableCommand {
    @Execute
    @Permission("nexus.smithingtable")
    @CommandDocs(description = "Opens a smithing table.")
    void executeSelf(@Context Player sender) {
        sender.openSmithingTable(null, true);
    }

    @Execute
    @Permission("nexus.smithingtable.other")
    @CommandDocs(description = "Opens a smithing table for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openSmithingTable(null, true);
    }
}
