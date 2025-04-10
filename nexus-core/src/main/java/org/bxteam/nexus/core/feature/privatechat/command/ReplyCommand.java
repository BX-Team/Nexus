package org.bxteam.nexus.core.feature.privatechat.command;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bxteam.nexus.docs.scan.command.CommandDocs;
import org.bxteam.nexus.feature.privatechat.PrivateChatService;

@Command(name = "reply", aliases = {"r"})
@Permission("nexus.reply")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ReplyCommand {
    private final PrivateChatService privateChatService;

    @Execute
    @CommandDocs(description = "Reply to a last received message", arguments = "<message>")
    void execute(@Context Player sender, @Join String message) {
        this.privateChatService.replyMessage(sender, message);
    }
}
