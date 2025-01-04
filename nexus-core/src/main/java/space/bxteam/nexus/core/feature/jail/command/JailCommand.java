package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.utils.DurationUtil;
import space.bxteam.nexus.feature.jail.JailPlayer;
import space.bxteam.nexus.feature.jail.JailService;

import java.time.Duration;

@Command(name = "jail")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class JailCommand {
    private final JailService jailService;
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;
    private final Server server;

    @Execute
    @Permission("nexus.jail")
    @CommandDocs(description = "Jail a player to specified jail name.", arguments = "<player> <jail>")
    void executeJail(@Context Player player, @Arg Player target, @Arg(JailCommandArgument.KEY) String name) {
        this.executeJailForTime(player, target, name, this.configurationProvider.configuration().jail().jailTime());
    }

    @Execute
    @Permission("nexus.jail")
    @CommandDocs(description = "Jail a player to specified jail name for specified time.", arguments = "<player> <jail> <duration>")
    void executeJailForTime(@Context Player player, @Arg Player target, @Arg(JailCommandArgument.KEY) String name, @Arg Duration duration) {
        if (target.hasPermission("nexus.jail.bypass")) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailCannotJailAdmin())
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            return;
        }

        if (player == target) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailCannotJailSelf())
                    .send();
            return;
        }

        if (this.jailService.isPlayerJailed(target.getUniqueId())) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailAlreadyJailed())
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            return;
        }

        this.jailService.jailPlayer(name, target, player, duration);
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.jail().jailJailedPrivate())
                .send();

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailJailedExecutor())
                .placeholder("{PLAYER}", player.getName())
                .send();
    }

    @Execute(name = "list")
    @Permission("nexus.jail.list")
    @CommandDocs(description = "List all jailed players.")
    void executeList(@Context Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailListEmpty())
                    .send();
            return;
        }

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailListHeader())
                .send();

        for (JailPlayer jailPlayer : this.jailService.getJailedPlayers()) {
            this.multificationManager.create()
                    .notice(translation -> translation.jail().jailListEntry())
                    .placeholder("{PLAYER}", this.server.getOfflinePlayer(jailPlayer.playerUuid()).getName())
                    .placeholder("{REMAINING_TIME}", DurationUtil.format(jailPlayer.getRemainingTime()))
                    .placeholder("{JAILED_BY}", jailPlayer.jailedBy())
                    .player(player.getUniqueId())
                    .send();
        }
    }
}
