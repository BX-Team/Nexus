package space.bxteam.nexus.core.feature.essentials.container;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "loom")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class LoomCommand {
    private final MessageManager messageManager;

    @Execute
    @Permission("nexus.loom")
    void executeSelf(@Context Player sender) {
        sender.openLoom(null, true);
    }

    @Execute
    @Permission("nexus.loom.other")
    void executeOther(@Context Player player, @Arg Player target) {
        target.openLoom(null, true);
    }
}
