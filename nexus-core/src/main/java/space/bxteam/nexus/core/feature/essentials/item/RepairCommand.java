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
import org.bukkit.inventory.meta.Repairable;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

import java.util.Arrays;

@Command(name = "repair")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RepairCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.repair")
    @CommandDocs(description = "Repair the item in your hand.")
    void repair(@Context Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (!isRepairable(handItem)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        Damageable damageable = (Damageable) handItem.getItemMeta();
        if (damageable.getDamage() == 0) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noDamaged())
                    .send();
            return;
        }

        this.repairItem(handItem);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().repairMessage())
                .send();
    }

    @Execute(name = "all")
    @Permission("nexus.repair.all")
    @CommandDocs(description = "Repair all items in your inventory.")
    void repairAll(@Context Player player) {
        ItemStack[] damagedItems = Arrays.stream(player.getInventory().getContents())
                .filter(this::isRepairable)
                .filter(item -> ((Damageable) item.getItemMeta()).getDamage() > 0)
                .toArray(ItemStack[]::new);

        if (damagedItems.length == 0) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noDamagedItems())
                    .send();
            return;
        }

        Arrays.stream(damagedItems).forEach(this::repairItem);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().repairAllMessage())
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
            itemStack.setItemMeta(damageable);
        }
    }
}
