package space.bxteam.nexus.core.feature.jail.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.jail.JailService;

@Command(name = "unjail", aliases = {"release"})
@Permission("nexus.jail.release")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class UnJailCommand {
    private final JailService jailService;
    private final MultificationManager multificationManager;

    @Execute
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

    @Execute
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

    @Execute(name = "-all", aliases = {"*"})
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
}
