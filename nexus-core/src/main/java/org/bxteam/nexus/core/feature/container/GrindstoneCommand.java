package org.bxteam.nexus.core.feature.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;

@Command(name = "grindstone")
public class GrindstoneCommand {
    @Execute
    @Permission("nexus.grindstone")
    @CommandDocs(description = "Opens a grindstone.")
    void executeSelf(@Context Player sender) {
        sender.openGrindstone(null, true);
    }

    @Execute
    @Permission("nexus.grindstone.other")
    @CommandDocs(description = "Opens a grindstone for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openGrindstone(null, true);
    }
}
