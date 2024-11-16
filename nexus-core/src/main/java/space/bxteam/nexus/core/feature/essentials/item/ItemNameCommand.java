package space.bxteam.nexus.core.feature.essentials.item;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "itemname", aliases = {"iname", "itemrename"})
@Permission("nexus.itemname")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ItemNameCommand {
    private final MultificationManager multificationManager;

    @Execute
    void execute(@Context Player player, @Join Component name) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);
        itemStack.setItemMeta(itemMeta);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().itemChangeNameMessage())
                .placeholder("{ITEM_NAME}", name.toString())
                .send();
    }

    @Execute(name = "clear")
    void clear(@Context Player player) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(null);
        itemStack.setItemMeta(itemMeta);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().itemClearNameMessage())
                .send();
    }

    private ItemStack checkItem(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR || itemStack.getItemMeta() == null) {
            return null;
        }

        return itemStack;
    }
}