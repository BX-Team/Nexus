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

@Command(name = "sun")
@Permission("nexus.sun")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SunCommand {
    private final MultificationManager multificationManager;
    private final Scheduler scheduler;

    @Execute
    @CommandDocs(description = "Set the weather to sunny.")
    void sun(@Context CommandSender sender, @Context World world) {
        this.setSun(sender, world);
    }

    @Execute
    @CommandDocs(description = "Set the weather to sunny in the specified world.", arguments = "<world>")
    void sunWorld(@Context CommandSender sender, @Arg World world) {
        this.setSun(sender, world);
    }

    private void setSun(CommandSender sender, World world) {
        this.scheduler.runTask(() -> {
            world.setClearWeatherDuration(20 * 60 * 10);
            world.setStorm(false);
            world.setThundering(false);
        });

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.timeAndWeather().weatherSetSun())
                .placeholder("{WORLD}", world.getName())
                .send();
    }
}
