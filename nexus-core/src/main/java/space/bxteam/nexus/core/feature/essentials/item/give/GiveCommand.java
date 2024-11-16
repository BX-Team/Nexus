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
import space.bxteam.nexus.core.configuration.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.utils.ItemUtil;
import space.bxteam.nexus.core.utils.MaterialUtil;

@Command(name = "give")
@Permission("nexus.give")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GiveCommand {
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    @Execute
    void execute(@Context Player player, @Arg Material material) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(player, material);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();
    }

    @Execute
    void execute(@Context CommandSender sender, @Arg Material material, @Arg Player target) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.item().giveGiven())
                .placeholder("{ITEM}", formattedMaterial)
                .placeholder("{PLAYER}", target.getName())
                .send();
    }

    @Execute
    void execute(@Context Player player, @Arg Material material, @Arg(GiveCommandArgument.KEY) int amount) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(player, material, amount);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();
    }

    @Execute
    void execute(@Context CommandSender sender, @Arg Material material, @Arg(GiveCommandArgument.KEY) int amount, @Arg Player target) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material, amount);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.item().giveReceived())
                .placeholder("{ITEM}", formattedMaterial)
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.item().giveGiven())
                .placeholder("{ITEM}", formattedMaterial)
                .placeholder("{PLAYER}", target.getName())
                .send();
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
        ItemStack item = ItemBuilder.from(material)
                .amount(amount)
                .build();

        ItemUtil.giveItem(player, item);
    }
}