package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "smithingtable", aliases = "smithing-table")
public class SmithingTableCommand {
    @Execute
    @Permission("nexus.smithingtable")
    void executeSelf(@Context Player sender) {
        sender.openSmithingTable(null, true);
    }

    @Execute
    @Permission("nexus.smithingtable.other")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openSmithingTable(null, true);
    }
}
