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
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "sun")
@Permission("nexus.sun")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SunCommand {
    private final MessageManager messageManager;

    @Execute
    void sun(@Context CommandSender sender, @Context World world) {
        this.setSun(sender, world);
    }

    @Execute
    void sunWorld(@Context CommandSender sender, @Arg World world) {
        this.setSun(sender, world);
    }

    private void setSun(CommandSender sender, World world) {
        world.setClearWeatherDuration(20 * 60 * 10);
        world.setStorm(false);
        world.setThundering(false);

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.timeAndWeather().weatherSetSun())
                .placeholder("{WORLD}", world.getName())
                .send();
    }
}
