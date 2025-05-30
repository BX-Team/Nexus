package org.bxteam.nexus.core.feature.randomteleport.command;

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
import org.bxteam.nexus.core.multification.MultificationFormatter;
import org.bxteam.nexus.feature.randomteleport.RandomTeleportService;

@Command(name = "randomteleport", aliases = {"rtp"})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RandomTeleportCommand {
    private static final MultificationFormatter<Player> PLACEHOLDERS = MultificationFormatter.<Player>builder()
            .with("{PLAYER}", Player::getName)
            .with("{WORLD}", player -> player.getWorld().getName())
            .with("{X}", player -> String.valueOf(player.getLocation().getBlockX()))
            .with("{Y}", player -> String.valueOf(player.getLocation().getBlockY()))
            .with("{Z}", player -> String.valueOf(player.getLocation().getBlockZ()))
            .build();

    private final MultificationManager multificationManager;
    private final RandomTeleportService randomTeleportService;

    @Execute
    @Permission("nexus.randomteleport")
    @CommandDocs(description = "Teleport to a random location.")
    void execute(@Context Player player) {
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.randomTeleport().randomTeleportSearchStart())
                .send();

        this.randomTeleportService.teleport(player).whenCompleteAsync((result, error) -> {
            if (error != null || !result.success()) {
                this.teleportFailureMessage(player);
                return;
            }

            this.teleportSuccessMessage(player);
        });
    }

    @Execute
    @Permission("nexus.randomteleport.other")
    @CommandDocs(description = "Teleport another player to a random location.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player player) {
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.randomTeleport().randomTeleportSearchStart())
                .send();

        this.randomTeleportService.teleport(player).whenCompleteAsync((result, error) -> {
            if (error != null || !result.success()) {
                this.teleportFailureMessage(player);
                return;
            }

            this.teleportSuccessMessage(player);
            this.senderSuccessMessage(sender, player);
        });
    }

    private void teleportFailureMessage(Player player) {
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.randomTeleport().randomTeleportSearchFailed())
                .send();
    }

    private void teleportSuccessMessage(Player player) {
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.randomTeleport().randomTeleportTeleported())
                .formatter(PLACEHOLDERS.toFormatter(player))
                .send();
    }

    private void senderSuccessMessage(CommandSender sender, Player target) {
        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.randomTeleport().teleportedSpecifiedPlayerToRandomLocation())
                .formatter(PLACEHOLDERS.toFormatter(target))
                .send();
    }
}
