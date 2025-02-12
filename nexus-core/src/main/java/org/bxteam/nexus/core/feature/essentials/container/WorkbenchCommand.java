package org.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;

@Command(name = "workbench")
public class WorkbenchCommand {
    @Execute
    @Permission("nexus.workbench")
    @CommandDocs(description = "Opens a workbench.")
    void executeSelf(@Context Player sender) {
        sender.openWorkbench(null, true);
    }

    @Execute
    @Permission("nexus.workbench.other")
    @CommandDocs(description = "Opens a workbench for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openWorkbench(null, true);
    }
}
