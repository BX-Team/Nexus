package org.bxteam.nexus.core.feature.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;

@Command(name = "loom")
public class LoomCommand {
    @Execute
    @Permission("nexus.loom")
    @CommandDocs(description = "Opens a loom.")
    void executeSelf(@Context Player sender) {
        sender.openLoom(null, true);
    }

    @Execute
    @Permission("nexus.loom.other")
    @CommandDocs(description = "Opens a loom for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openLoom(null, true);
    }
}
