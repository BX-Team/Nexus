package org.bxteam.nexus.core.integration.litecommands.handler;

import com.google.inject.Inject;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bxteam.nexus.core.multification.MultificationManager;
import org.bxteam.nexus.core.annotations.litecommands.LiteHandler;

@LiteHandler(InvalidUsage.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvalidUsageHandlerImpl implements InvalidUsageHandler<CommandSender> {
    private final MultificationManager multificationManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();

        if (schematic.isOnlyFirst()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.argument().usageMessage())
                    .placeholder("{USAGE}", schematic.first())
                    .send();
            return;
        }

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.argument().usageMessageHead())
                .send();
        for (String schema : schematic.all()) {
            this.multificationManager.create()
                    .viewer(sender)
                    .notice(translation -> translation.argument().usageMessageEntry())
                    .placeholder("{USAGE}", schema)
                    .send();
        }
    }
}
