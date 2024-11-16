package space.bxteam.nexus.core.integration.litecommands.handler;

import com.google.inject.Inject;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import space.bxteam.nexus.core.multification.MultificationManager;
import space.bxteam.nexus.core.scanner.annotations.litecommands.LiteHandler;

@LiteHandler(MissingPermissions.class)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PermissionsHandler implements MissingPermissionsHandler<CommandSender> {
    private final MultificationManager multificationManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        String permissions = missingPermissions.asJoinedText();
        CommandSender sender = invocation.sender();

        this.multificationManager.create()
                .viewer(sender)
                .notice(translation -> translation.argument().noPermission())
                .placeholder("{PERMISSIONS}", permissions)
                .send();
    }
}
