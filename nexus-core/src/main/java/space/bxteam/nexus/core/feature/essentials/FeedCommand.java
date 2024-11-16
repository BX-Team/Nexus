package space.bxteam.nexus.core.feature.essentials;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "feed")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FeedCommand {
    private final MultificationManager multificationManager;

    @Execute
    @Permission("nexus.feed")
    void execute(@Context Player player) {
        this.feed(player);

        this.multificationManager.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.player().feedMessage())
                .send();
    }

    @Execute
    @Permission("nexus.feed.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.feed(target);

        this.multificationManager.create()
                .player(target.getUniqueId())
                .notice(translation -> translation.player().feedMessage())
                .send();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.player().feedMessageBy())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }

    private void feed(Player player) {
        player.setFoodLevel(20);
    }
}
