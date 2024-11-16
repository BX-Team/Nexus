package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "anvil")
public class AnvilCommand {
    @Execute
    @Permission("nexus.anvil")
    void executeSelf(@Context Player sender) {
        sender.openAnvil(null, true);
    }

    @Execute
    @Permission("nexus.anvil.other")
    void executeOther(@Context Player sender, @Arg Player target) {
        target.openAnvil(null, true);
    }
}