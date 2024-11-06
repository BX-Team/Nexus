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
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "fly")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FlyCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.fly")
    void execute(@Context Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.messageManager.create()
                .player(player)
                .message(translation -> player.getAllowFlight() ? translation.player().flyEnable() : translation.player().flyDisable())
                .placeholder("{STATE}", translation -> player.getAllowFlight() ? translation.format().enable() : translation.format().disable())
                .send();
    }

    @Execute
    @Permission("nexus.fly.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        target.setAllowFlight(!target.getAllowFlight());

        this.messageManager.create()
                .player(target.getUniqueId())
                .message(translation -> target.getAllowFlight() ? translation.player().flyEnable() : translation.player().flyDisable())
                .placeholder("{STATE}", translation -> target.getAllowFlight() ? translation.format().enable() : translation.format().disable())
                .send();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> target.getAllowFlight() ? translation.player().flySetEnable() : translation.player().flySetDisable())
                .placeholder("{PLAYER}", target.getName())
                .placeholder("{STATE}", translation -> target.getAllowFlight() ? translation.format().enable() : translation.format().disable())
                .send();
    }
}
