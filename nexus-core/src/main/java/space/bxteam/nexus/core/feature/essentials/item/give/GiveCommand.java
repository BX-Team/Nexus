package space.bxteam.nexus.core.feature.essentials.item.give;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import space.bxteam.commons.bukkit.inventory.ItemUtil;
import space.bxteam.commons.bukkit.inventory.MaterialUtil;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "give")
@Permission("nexus.give")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GiveCommand {
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    @Execute
    @CommandDocs(description = "Give an item to a player.", arguments = "<player> <item>")
    void execute(@Context CommandSender sender, @Arg Player target, @Arg Material material) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material);

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();

        if (!sender.equals(target)) {
            this.multificationManager.create()
                    .player(target.getUniqueId())
                    .notice(translation -> translation.item().giveGiven())
                    .placeholder("{ITEM}", formattedMaterial)
                    .send();
        }
    }

    @Execute
    @CommandDocs(description = "Give an item to a player with specified amount", arguments = "<player> <item> <amount>")
    void execute(@Context CommandSender sender, @Arg Player target, @Arg Material material, @Arg(GiveAmountArgument.KEY) int amount) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material, amount);

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();

        if (!sender.equals(target)) {
            this.multificationManager.create()
                    .player(target.getUniqueId())
                    .notice(translation -> translation.item().giveGiven())
                    .placeholder("{ITEM}", formattedMaterial)
                    .send();
        }
    }

    private void giveItem(Player player, Material material) {
        int amount = configurationProvider.configuration().items().defaultGiveAmount();

        if (!material.isItem()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.item().incorrectItem())
                    .send();
            return;
        }

        ItemStack item = ItemBuilder.from(material)
                .amount(amount)
                .build();

        ItemUtil.giveItem(player, item);
    }

    private void giveItem(Player player, Material material, int amount) {
        if (!material.isItem()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.item().incorrectItem())
                    .send();
            return;
        }

        ItemStack item = ItemBuilder.from(material)
                .amount(amount)
                .build();

        ItemUtil.giveItem(player, item);
    }
}
