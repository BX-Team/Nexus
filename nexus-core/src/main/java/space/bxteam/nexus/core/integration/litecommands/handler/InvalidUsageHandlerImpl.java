package space.bxteam.nexus.core.integration.litecommands.handler;

import com.google.inject.Inject;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteHandler;
import space.bxteam.nexus.core.message.MessageManager;

@LiteHandler(InvalidUsage.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvalidUsageHandlerImpl implements InvalidUsageHandler<CommandSender> {
    private final MessageManager messageManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            this.messageManager.create()
                    .recipient(sender)
                    .message(translation -> translation.argument().usageMessage())
                    .placeholder("{USAGE}", schematic.first())
                    .send();
            return;
        }

        this.messageManager.create().recipient(sender).message(translation -> translation.argument().usageMessageHead()).send();
        for (String schema : schematic.all()) {
            this.messageManager.create()
                    .recipient(sender)
                    .message(translation -> translation.argument().usageMessageEntry())
                    .placeholder("{USAGE}", schema)
                    .send();
        }
    }
}
