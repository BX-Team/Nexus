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
import space.bxteam.nexus.core.multification.MultificationManager;

@Command(name = "sudo")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SudoCommand {
    private final Server server;
    private final MultificationManager multificationManager;

    @Execute(name = "-console")
    @Permission("nexus.sudo.console")
    void console(@Context CommandSender sender, @Join String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
        this.sendResultMessage(sender, this.server.getConsoleSender(), command);
    }

    @Execute
    @Permission("nexus.sudo.player")
    void player(@Context CommandSender sender, @Arg Player target, @Join String command) {
        this.server.dispatchCommand(target, command);
        this.sendResultMessage(sender, target, command);
    }

    private void sendResultMessage(CommandSender sender, CommandSender target, String command) {
        this.server.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("nexus.sudo.spy"))
                .forEach(player -> this.multificationManager.create()
                        .player(player.getUniqueId())
                        .notice(translation -> translation.sudo().sudoMessageSpy())
                        .placeholder("{PLAYER}", sender.getName())
                        .placeholder("{TARGET}", target.getName())
                        .placeholder("{COMMAND}", command)
                        .send());
    }
}
