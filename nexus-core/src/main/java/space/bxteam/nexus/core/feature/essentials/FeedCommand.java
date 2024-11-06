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
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "feed")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FeedCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.feed")
    void execute(@Context Player player) {
        this.feed(player);

        this.messageManager.create()
                .player(player)
                .message(translation -> translation.player().feedMessage())
                .send();
    }

    @Execute
    @Permission("nexus.feed.other")
    void execute(@Context CommandSender sender, @Arg Player target) {
        this.feed(target);

        this.messageManager.create()
                .player(target.getUniqueId())
                .message(translation -> translation.player().feedMessage())
                .send();

        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.player().feedMessageBy())
                .placeholder("{PLAYER}", target.getName())
                .send();
    }

    private void feed(Player player) {
        player.setFoodLevel(20);
    }
}
