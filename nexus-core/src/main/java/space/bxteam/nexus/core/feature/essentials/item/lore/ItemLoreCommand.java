package space.bxteam.nexus.core.feature.essentials.item.lore;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import dev.rollczi.litecommands.annotations.argument.Arg;
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

import java.util.ArrayList;
import java.util.List;

@Command(name = "itemlore", aliases = {"lore"})
@Permission("nexus.itemlore")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ItemLoreCommand {
    private final MultificationManager multificationManager;
    @Named("colorMiniMessage")
    private final MiniMessage miniMessage;

    @Execute
    @CommandDocs(description = "Change the lore of an item.", arguments = "<line> <text>")
    void execute(@Context Player player, @Arg(ItemLoreArgument.KEY) int line, @Join String text) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        lore = lore == null ? new ArrayList<>() : new ArrayList<>(lore);

        if (text.equals("none")) {
            lore.remove(line);
        }
        else {
            while (lore.size() <= line) {
                lore.add("");
            }

            lore.set(line, AdventureUtil.SECTION_SERIALIZER.serialize(AdventureUtil.resetItalic(this.miniMessage.deserialize(text))));
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().itemChangeLoreMessage())
                .placeholder("{ITEM_LORE}", text)
                .send();
    }

    @Execute(name = "clear")
    @CommandDocs(description = "Clear the lore of an item.")
    void clear(@Context Player player) {
        ItemStack itemStack = this.checkItem(player);

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().itemClearLoreMessage())
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
