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

@Command(name = "god", aliases = "godmode")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GodCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.god")
    void execute(@Context Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.messageManager.create()
                .player(player)
                .message(translation -> player.isInvulnerable()
                        ? translation.player().godEnable()
                        : translation.player().godDisable())
                .placeholder("{STATE}", translation -> player.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();
    }

    @Execute
    @Permission("nexus.god.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        target.setInvulnerable(!target.isInvulnerable());

        this.messageManager.create()
                .player(target.getUniqueId())
                .message(translation -> target.isInvulnerable()
                        ? translation.player().godEnable()
                        : translation.player().godDisable())
                .placeholder("{STATE}", translation -> target.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> target.isInvulnerable()
                        ? translation.player().godEnable()
                        : translation.player().godDisable())
                .placeholder("{STATE}", translation -> target.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();
    }
}
