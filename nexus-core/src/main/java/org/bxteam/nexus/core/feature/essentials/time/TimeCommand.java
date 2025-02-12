package org.bxteam.nexus.core.feature.essentials.time;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bxteam.commons.scheduler.Scheduler;
import org.bxteam.nexus.annotations.scan.command.CommandDocs;
import org.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "time")
@Permission("nexus.time")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TimeCommand {
    private final MultificationManager multificationManager;
    private final Scheduler scheduler;

    @Execute(name = "add")
    @CommandDocs(description = "Add time to the world.", arguments = "<time>")
    void add(@Context Player player, @Context CommandSender sender, @Arg(TimeCommandArgument.KEY) int time) {
        this.add(sender, time, player.getWorld());
    }

    @Execute(name = "add")
    @CommandDocs(description = "Add time to the world.", arguments = "<time> <world>")
    void add(@Context CommandSender sender, @Arg(TimeCommandArgument.KEY) int time, @Arg World world) {
        this.scheduler.runTask(() -> {
            world.setTime(world.getTime() + time);
        });

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.timeAndWeather().timeAdd())
                .placeholder("{TIME}", String.valueOf(time))
                .send();
    }

    @Execute(name = "set")
    @CommandDocs(description = "Set the time in the world.", arguments = "<time>")
    void set(@Context Player player, @Context CommandSender sender, @Arg(TimeCommandArgument.KEY) int time) {
        this.set(sender, time, player.getWorld());
    }

    @Execute(name = "set")
    @CommandDocs(description = "Set the time in the world.", arguments = "<time> <world>")
    void set(@Context CommandSender sender, @Arg(TimeCommandArgument.KEY) int time, @Arg World world) {
        this.scheduler.runTask(() -> {
            world.setTime(time);
        });

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.timeAndWeather().timeSet())
                .placeholder("{TIME}", String.valueOf(time))
                .send();
    }

    @Execute(name = "set day")
    @CommandDocs(description = "Set the time to day.")
    void setDay(@Context Player player, @Context CommandSender sender) {
        this.set(sender, 1000, player.getWorld());
    }

    @Execute(name = "set night")
    @CommandDocs(description = "Set the time to night.")
    void setNight(@Context Player player, @Context CommandSender sender) {
        this.set(sender, 13000, player.getWorld());
    }

    @Execute(name = "set noon")
    @CommandDocs(description = "Set the time to noon.")
    void setNoon(@Context Player player, @Context CommandSender sender) {
        this.set(sender, 6000, player.getWorld());
    }

    @Execute(name = "set midnight")
    @CommandDocs(description = "Set the time to midnight.")
    void setMidnight(@Context Player player, @Context CommandSender sender) {
        this.set(sender, 18000, player.getWorld());
    }
}
