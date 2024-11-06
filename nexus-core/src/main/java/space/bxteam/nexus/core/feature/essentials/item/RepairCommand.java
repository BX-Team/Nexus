package space.bxteam.nexus.core.feature.essentials.item;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import space.bxteam.nexus.core.message.MessageManager;

import java.util.Arrays;

@Command(name = "repair")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RepairCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.repair")
    void repair(@Context Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (!isRepairable(handItem)) {
            this.messageManager.create()
                    .message(translation -> translation.argument().noItem())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        Damageable damageable = (Damageable) handItem.getItemMeta();
        if (damageable.getDamage() == 0) {
            this.messageManager.create()
                    .message(translation -> translation.argument().noDamaged())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        this.repairItem(handItem);
        this.messageManager.create()
                .message(translation -> translation.item().repairMessage())
                .player(player.getUniqueId())
                .send();
    }

    @Execute(name = "all")
    @Permission("nexus.repair.all")
    void repairAll(@Context Player player) {
        ItemStack[] damagedItems = Arrays.stream(player.getInventory().getContents())
                .filter(this::isRepairable)
                .filter(item -> ((Damageable) item.getItemMeta()).getDamage() > 0)
                .toArray(ItemStack[]::new);

        if (damagedItems.length == 0) {
            this.messageManager.create()
                    .message(translation -> translation.argument().noDamagedItems())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        Arrays.stream(damagedItems).forEach(this::repairItem);
        this.messageManager.create()
                .message(translation -> translation.item().repairAllMessage())
                .player(player.getUniqueId())
                .send();
    }

    private boolean isRepairable(ItemStack itemStack) {
        return itemStack != null
                && itemStack.getItemMeta() instanceof Repairable
                && itemStack.getItemMeta() instanceof Damageable;
    }

    private void repairItem(ItemStack itemStack) {
        Damageable damageable = (Damageable) itemStack.getItemMeta();
        if (damageable != null) {
            damageable.setDamage(0);
            itemStack.setItemMeta((ItemMeta) damageable);
        }
    }
}
