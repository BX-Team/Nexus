package space.bxteam.nexus.core.feature.essentials.container;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "clear")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ClearCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.clear")
    void execute(@Context Player player) {
        this.clear(player);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.inventory().inventoryClearMessage())
                .send();
    }

    @Execute
    @Permission("nexus.clear.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.clear(target);

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.inventory().inventoryClearMessageBy())
                .placeholder("{PLAYER}", target.getName())
                .send();

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.inventory().inventoryClearMessage())
                .send();
    }

    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();
    }
}
