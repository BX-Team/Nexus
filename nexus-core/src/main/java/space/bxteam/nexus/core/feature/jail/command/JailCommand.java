package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
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
public class JailCommand { // TODO: Split commands into separate classes
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

    @Execute(name = "release")
    @Permission("nexus.jail.release")
    @CommandDocs(description = "Releases you from jail.")
    void executeRelease(@Context Player player) {
        if (!this.jailService.isPlayerJailed(player.getUniqueId())) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailNotJailed())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            return;
        }

        this.jailService.releasePlayer(player);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailReleasePrivate())
                .send();
    }

    @Execute(name = "release")
    @Permission("nexus.jail.release")
    @CommandDocs(description = "Releases a player from jail.", arguments = "<player>")
    void executeRelease(@Context Player player, @Arg Player target) {
        if (!this.jailService.isPlayerJailed(target.getUniqueId())) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailNotJailed())
                    .placeholder("{PLAYER}", target.getName())
                    .send();
            return;
        }

        this.jailService.releasePlayer(target);
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.jail().jailReleasePrivate())
                .send();

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailReleaseExecutor())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }

    @Execute(name = "release -all", aliases = {"release *"})
    @Permission("nexus.jail.release")
    @CommandDocs(description = "Releases all players from jail.")
    void executeReleaseAll(@Context Player player) {
        if (this.jailService.getJailedPlayers().isEmpty()) {
            this.multificationManager.create()
                    .notice(translation -> translation.jail().jailReleaseNoPlayers())
                    .player(player.getUniqueId())
                    .send();
            return;
        }

        this.jailService.releaseAllPlayers();
        this.multificationManager.create()
                .notice(translation -> translation.jail().jailReleaseAll())
                .player(player.getUniqueId())
                .send();
    }

    @Execute(name = "setup")
    @Permission("nexus.jail.setup")
    @CommandDocs(description = "Set up a jail location with specified name.", arguments = "<name>")
    void executeSetup(@Context Player player, @Arg String name) {
        if (this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailLocationExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        Location location = player.getLocation();

        this.jailService.createJailLocation(name, location);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailLocationSet())
                .placeholder("{JAIL}", name)
                .send();
    }

    @Execute(name = "setup")
    @Permission("nexus.jail.setup")
    @CommandDocs(description = "Set up a jail location with specified name and location.", arguments = "<name> <location>")
    void executeSetup(@Context Player player, @Arg String name, @Arg Location location) {
        if (this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.jail().jailLocationExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        location.setWorld(player.getWorld());

        this.jailService.createJailLocation(name, location);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.jail().jailLocationSet())
                .placeholder("{JAIL}", name)
                .send();
    }

    @Execute(name = "remove")
    @Permission("nexus.jail.remove")
    @CommandDocs(description = "Remove a jail location with specified name.", arguments = "<name>")
    void executeRemove(@Context CommandSender sender, @Arg(JailCommandArgument.KEY) String name) {
        if (!this.jailService.jailExists(name)) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.jail().jailLocationNotExists())
                    .placeholder("{JAIL}", name)
                    .send();
            return;
        }

        this.jailService.removeJailArea(name);
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.jail().jailLocationRemove())
                .placeholder("{JAIL}", name)
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
                    .placeholder("{PLAYER}", this.server.getOfflinePlayer(jailPlayer.getPlayerUniqueId()).getName())
                    .placeholder("{REMAINING_TIME}", DurationUtil.format(jailPlayer.getRemainingTime()))
                    .placeholder("{JAILED_BY}", jailPlayer.getJailedBy())
                    .player(player.getUniqueId())
                    .send();
        }
    }
}
