package space.bxteam.nexus.core.feature.essentials;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "heal")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HealCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.heal")
    void execute(@Context Player player) {
        this.heal(player);

        this.messageManager.create()
                .player(player)
                .message(translation -> translation.player().healMessage())
                .send();
    }

    @Execute
    @Permission("nexus.heal.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.heal(target);

        this.messageManager.create()
                .message(translation -> translation.player().healMessage())
                .player(target.getUniqueId())
                .send();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().healMessageBy())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }

    private void heal(Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }
}
