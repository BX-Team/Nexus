package org.bxteam.nexus.core.feature.weather;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bxteam.commons.scheduler.Scheduler;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "rain")
@Permission("nexus.rain")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RainCommand {
    private final MultificationManager multificationManager;
    private final Scheduler scheduler;

    @Execute
    @CommandDocs(description = "Set rain in the world.")
    void rain(@Context CommandSender sender, @Context World world) {
        this.setRain(sender, world);
    }

    @Execute
    @CommandDocs(description = "Set rain in the specified world.", arguments = "<world>")
    void rainWorld(@Context CommandSender sender, @Arg World world) {
        this.setRain(sender, world);
    }

    private void setRain(CommandSender sender, World world) {
        this.scheduler.runTask(() -> {
            world.setStorm(true);
            world.setThundering(false);
        });

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.timeAndWeather().weatherSetRain())
                .placeholder("{WORLD}", world.getName())
                .send();
    }
}
