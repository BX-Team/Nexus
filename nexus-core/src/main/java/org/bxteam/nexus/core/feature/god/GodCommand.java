package org.bxteam.nexus.core.feature.god;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "god", aliases = "godmode")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GodCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.god")
    @CommandDocs(description = "Enable or disable god mode.")
    void execute(@Context Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> player.isInvulnerable()
                        ? translation.player().godEnable()
                        : translation.player().godDisable())
                .placeholder("{STATE}", translation -> player.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();
    }

    @Execute
    @Permission("nexus.god.other")
    @CommandDocs(description = "Enable or disable god mode for another player.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        target.setInvulnerable(!target.isInvulnerable());

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> target.isInvulnerable()
                        ? translation.player().godEnable()
                        : translation.player().godDisable())
                .placeholder("{STATE}", translation -> target.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> target.isInvulnerable()
                        ? translation.player().godSetEnable()
                        : translation.player().godSetDisable())
                .placeholder("{STATE}", translation -> target.isInvulnerable()
                        ? translation.format().enable()
                        : translation.format().disable())
                .send();
    }
}
