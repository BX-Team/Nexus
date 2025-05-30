package org.bxteam.nexus.core.feature.item;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bxteam.commons.bukkit.inventory.ItemUtil;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "hat")
@Permission("nexus.hat")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HatCommand {
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Put an item on your head.")
    void execute(@Context Player player) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (handItem == null) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.argument().noItem())
                    .send();
            return;
        }

        ItemStack singleItem = handItem.clone();
        singleItem.setAmount(1);
        handItem.setAmount(handItem.getAmount() - 1);

        if (itemStack != null) {
            ItemUtil.giveItem(player, itemStack);
        }

        playerInventory.setHelmet(singleItem);
    }
}
