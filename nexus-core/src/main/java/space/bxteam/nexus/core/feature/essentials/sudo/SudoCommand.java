package space.bxteam.nexus.core.feature.essentials.sudo;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

@Command(name = "sudo")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SudoCommand {
    private final Server server;
    private final MessageManager messageManager;

    @Execute(name = "-console")
    @Permission("nexus.sudo.console")
    void console(@Context CommandSender sender, @Join String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
        this.sendResultMessage(sender, command);
    }

    @Execute
    @Permission("nexus.sudo.player")
    void player(@Context CommandSender sender, @Arg Player target, @Join String command) {
        this.server.dispatchCommand(target, command);
        this.sendResultMessage(sender, command);
    }

    private void sendResultMessage(CommandSender sender, String command) {
        this.messageManager.create()
                .recipient(sender)
                .message(translation -> translation.sudo().sudoMessage())
                .placeholder("{COMMAND}", command)
                .placeholder("{PLAYER}", sender.getName())
                .send();

        this.server.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("nexus.sudo.spy"))
                .forEach(player -> this.messageManager.create()
                        .message(translation -> translation.sudo().sudoMessageSpy())
                        .placeholder("{COMMAND}", command)
                        .placeholder("{PLAYER}", sender.getName())
                        .player(player.getUniqueId())
                        .send());
    }
}
