package space.bxteam.nexus.core.feature.essentials.gamemode;

import com.google.inject.Inject;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.bxteam.nexus.core.message.MessageManager;

@RootCommand
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class GamemodeCommand {
    private final MessageManager messageManager;

    @Execute(name = "gmc", aliases = {"creative", "gm1"})
    @Permission("nexus.gamemode.creative")
    void creative(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);
            player.setGameMode(GameMode.CREATIVE);
            return;
        }

        target.setGameMode(GameMode.CREATIVE);
    }

}
