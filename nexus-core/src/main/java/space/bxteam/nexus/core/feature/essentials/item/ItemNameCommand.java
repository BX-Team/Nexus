package space.bxteam.nexus.core.feature.essentials.item;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.bxteam.commons.adventure.AdventureUtil;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "itemname", aliases = {"iname", "itemrename"})
@Permission("nexus.itemname")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ItemNameCommand {
    private final MultificationManager multificationManager;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @Execute
    @CommandDocs(description = "Change the name of an item.", arguments = "<name>")
    void execute(@Context Player player, @Join String name) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        String serialized = AdventureUtil.SECTION_SERIALIZER.serialize(this.miniMessage.deserialize(name));
        itemMeta.setDisplayName(serialized);
        itemStack.setItemMeta(itemMeta);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().itemChangeNameMessage())
                .placeholder("{ITEM_NAME}", name)
                .send();
    }

    @Execute(name = "clear")
    @CommandDocs(description = "Clear the name of an item.")
    void clear(@Context Player player) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(null);
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
