package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "grindstone")
public class GrindstoneCommand {
    @Execute
    @Permission("nexus.grindstone")
    void executeSelf(@Context Player sender) {
        sender.openGrindstone(null, true);

    }

    @Execute
    @Permission("nexus.grindstone.other")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openGrindstone(null, true);
    }
}
