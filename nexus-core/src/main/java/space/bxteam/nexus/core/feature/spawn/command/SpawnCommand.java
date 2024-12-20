package space.bxteam.nexus.core.feature.spawn.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.commons.bukkit.position.Position;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.feature.spawn.SpawnService;

@Command(name = "spawn")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpawnCommand {
    private final SpawnService spawnService;
    private final MultificationManager multificationManager;
    private final PluginConfigurationProvider configurationProvider;

    @Execute
    @Permission("nexus.spawn")
    @CommandDocs(description = "Teleport to the spawn.")
    void execute(@Context Player player) {
        Position spawn = this.configurationProvider.configuration().spawn().location();

        if (spawn.isNoneWorld()) {
            this.multificationManager.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.spawn().noSpawn())
                    .send();
            return;
        }

        this.spawnService.teleportToSpawn(player);
        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.spawn().spawnTeleported())
                .send();
    }

    @Execute
    @Permission("nexus.spawn.other")
    @CommandDocs(description = "Teleport another player to the spawn.", arguments = "<player>")
    void execute(@Context CommandSender sender, @Arg Player target) {
        Position spawn = this.configurationProvider.configuration().spawn().location();

        if (spawn.isNoneWorld()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.spawn().noSpawn())
                    .send();
            return;
        }

        this.spawnService.teleportToSpawn(target);
        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.spawn().spawnTeleported())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.spawn().spawnTeleportedOther())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }
}
