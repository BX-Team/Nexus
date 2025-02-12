package org.bxteam.nexus.core.feature.fun;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "spit")
@Permission("nexus.spit")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SpitCommand {
    private final MultificationManager multificationManager;

    @Execute
    @CommandDocs(description = "Just spit.")
    void execute(@Context Player player) {
        Location location = player.getEyeLocation();
        World world = location.getWorld();

        location.setY(location.getY() - 0.5);
        this.multificationManager.player(player.getUniqueId(), translation -> translation.fun().spitSound());
        LlamaSpit spit = (LlamaSpit) world.spawnEntity(location, org.bukkit.entity.EntityType.LLAMA_SPIT);
        spit.setVelocity(location.getDirection());
    }
}
