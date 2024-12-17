package space.bxteam.nexus.core.feature.essentials.weather;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import space.bxteam.commons.scheduler.Scheduler;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "thunder")
@Permission("nexus.thunder")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ThunderCommand {
    private final MultificationManager multificationManager;
    private final Scheduler scheduler;

    @Execute
    void thunder(@Context CommandSender sender, @Context World world) {
        this.setThunder(sender, world);
    }

    @Execute
    void thunderWorld(@Context CommandSender sender, @Arg World world) {
        this.setThunder(sender, world);
    }

    private void setThunder(CommandSender sender, World world) {
        this.scheduler.runTask(() -> {
            world.setStorm(true);
            world.setThundering(true);
        });

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.timeAndWeather().weatherSetThunder())
                .placeholder("{WORLD}", world.getName())
                .send();
    }
}
