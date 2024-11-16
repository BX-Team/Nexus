package space.bxteam.nexus.core.feature.essentials.item.enchant;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "enchant")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EnchantCommand {
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    @Execute
    @Permission("nexus.enchant")
    void execute(@Context Player player, @Arg Enchantment enchantment, @Arg(EnchantCommandArgument.KEY) int level) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        this.enchantItem(player, handItem, enchantment, level);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().enchantedMessage())
                .send();
    }

    @Execute
    @Permission("nexus.enchant.other")
    void execute(@Context Player sender, @Arg Enchantment enchantment, @Arg(EnchantCommandArgument.KEY) int level, @Arg Player target) {
        PlayerInventory targetInventory = target.getInventory();
        ItemStack handItem = targetInventory.getItem(targetInventory.getHeldItemSlot());

        if (handItem == null) {
            this.multificationManager.create()
                    .player(sender.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        this.enchantItem(target, handItem, enchantment, level);

        this.multificationManager.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.item().enchantedMessageFor())
                .placeholder("{PLAYER}", target.getName())
                .send();

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.item().enchantedMessageBy())
                .placeholder("{PLAYER}", sender.getName())
                .send();
    }

    private void enchantItem(Player player, ItemStack item, Enchantment enchantment, int level) {
        if (this.configurationProvider.configuration().items().unsafeEnchantments()) {
            item.addUnsafeEnchantment(enchantment, level);
            return;
        }

        if (enchantment.getStartLevel() > level || enchantment.getMaxLevel() < level || !enchantment.canEnchantItem(item)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noValidEnchantmentLevel())
                    .send();
            return;
        }

        item.addEnchantment(enchantment, level);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().enchantedMessage())
                .send();
    }
}
