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
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "heal")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HealCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.heal")
    @CommandDocs(description = "Heal yourself.")
    void execute(@Context Player player) {
        this.heal(player);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.player().healMessage())
                .send();
    }

    @Execute
    @Permission("nexus.heal.other")
    @CommandDocs(description = "Heal another player.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.heal(target);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.player().healMessage())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().healMessageBy())
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
