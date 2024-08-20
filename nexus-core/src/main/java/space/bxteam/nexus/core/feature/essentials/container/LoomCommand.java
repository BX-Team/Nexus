package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "loom")
public class LoomCommand {
    @Execute
    @Permission("nexus.loom")
    void executeSelf(@Context Player sender) {
        sender.openLoom(null, true);
    }

    @Execute
    @Permission("nexus.loom.other")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openLoom(null, true);
    }
}
