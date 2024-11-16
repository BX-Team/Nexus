package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "workbench")
public class WorkbenchCommand {
    @Execute
    @Permission("nexus.workbench")
    void executeSelf(@Context Player sender) {
        sender.openWorkbench(null, true);
    }

    @Execute
    @Permission("nexus.workbench.other")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openWorkbench(null, true);
    }
}
