package space.bxteam.nexus.core.integration.litecommands.handler;

import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteHandler;

@LiteHandler(NoticeBroadcast.class)
public class NoticeBroadcastHandler implements ResultHandler<CommandSender, NoticeBroadcast> {
    @Override
    public void handle(Invocation<CommandSender> invocation, NoticeBroadcast result, ResultHandlerChain<CommandSender> chain) {
        result.send();
    }
}
