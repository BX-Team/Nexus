package org.bxteam.nexus.core.feature.spawn.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.feature.spawn.SpawnService;

@Command(name = "setspawn")
@Permission("nexus.setspawn")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SetSpawnCommand {
    private final SpawnService spawnService;
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Set the spawn location.")
    void execute(@Context Player player) {
        this.spawnService.setSpawnLocation(player.getLocation());

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.spawn().setSpawn())
                .send();
    }
}
