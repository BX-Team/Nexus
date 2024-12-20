package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;

@Command(name = "stonecutter")
public class StonecutterCommand {
    @Execute
    @Permission("nexus.stonecutter")
    @CommandDocs(description = "Opens a stonecutter.")
    void executeSelf(@Context Player sender) {
        sender.openStonecutter(null, true);
    }

    @Execute
    @Permission("nexus.stonecutter.other")
    @CommandDocs(description = "Opens a stonecutter for another player.", arguments = "<player>")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openStonecutter(null, true);
    }
}
