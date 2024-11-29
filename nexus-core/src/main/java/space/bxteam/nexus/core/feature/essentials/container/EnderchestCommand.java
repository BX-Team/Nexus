package space.bxteam.nexus.core.feature.essentials.container;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "enderchest", aliases = { "ec" })
public class EnderchestCommand {

    @Execute
    @Permission("nexus.enderchest")
    void execute(@Context Player player) {
        player.openInventory(player.getEnderChest());
    }

    @Execute
    @Permission("nexus.enderchest.other")
    void execute(@Context Player player, @Arg Player target) {
        player.openInventory(target.getEnderChest());
    }
}
