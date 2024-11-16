package space.bxteam.nexus.core.feature.essentials.playerinfo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "whois")
@Permission("nexus.whois")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class WhoisCommand {
    private final MultificationManager multificationManager;

    @Execute
    void execute(@Context CommandSender sender, @Arg Player player) {
        /*
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().whoisCommand())
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
                .placeholder("{IP}", player.getAddress().getHostString())
                .placeholder("{GAMEMODE}", player.getGameMode().name())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .placeholder("{HEALTH}", String.valueOf(player.getHealth()))
                .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
                .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
                .send();
         */ // Commented out until we will found a way to send List<?> as a message
    }
}
