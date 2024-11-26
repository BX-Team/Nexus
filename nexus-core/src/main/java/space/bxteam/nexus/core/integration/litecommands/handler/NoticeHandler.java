package space.bxteam.nexus.core.integration.litecommands.handler;

import com.eternalcode.multification.notice.Notice;
import com.google.inject.Inject;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteHandler;

@LiteHandler(Notice.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NoticeHandler implements ResultHandler<CommandSender, Notice> {
    private final MultificationManager multificationManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, Notice result, ResultHandlerChain<CommandSender> chain) {
        this.multificationManager.create()
                .viewer(invocation.sender())
                .notice(result)
                .send();
    }
}
