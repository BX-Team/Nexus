package space.bxteam.nexus.core.feature.privatechat.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.annotations.scan.command.CommandDocs;
import space.bxteam.nexus.feature.privatechat.PrivateChatService;

@Command(name = "message", aliases = {"msg", "m", "whisper", "w", "tell", "t"})
@Permission("nexus.message")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MessageCommand {
    private final PrivateChatService privateChatService;

    @Execute
    @CommandDocs(description = "Send a private message to a player", arguments = "<player> <message>")
    void execute(@Context Player sender, @Arg Player target, @Join String message) {
        this.privateChatService.sendMessage(sender, target, message);
    }
}
