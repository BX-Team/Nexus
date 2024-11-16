package space.bxteam.nexus.core.feature.essentials.chat;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "broadcast", aliases = {"bc"})
@Permission("nexus.broadcast")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BroadcastCommand {
    private final MultificationManager multificationManager;

    @Execute
    void execute(@Context CommandSender sender, @Join String message) {
        this.multificationManager.create()
                .all()
                .notice(translation -> translation.chat().broadcastMessage())
                .placeholder("{MESSAGE}", message)
                .send();
    }
}
