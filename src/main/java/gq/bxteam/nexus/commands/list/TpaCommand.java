package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.chat.TextComponent;

public class TpaCommand extends CommandBase implements CommandExecutor {
    public TpaCommand()
    {
        super("tpa", "", "/tpa <to>", "", "nexus.command.tp");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("tpa-usage")));
            return;
        }
        else if (args[0] == null)
        {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("tpa-usage")));
            return;
        }

        if (sender instanceof Player)
        {
            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.tpa")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("no-permission")));
                return;
            }

            if (args[0] == "accept")
            {
                return;
            }

            else if (args[0] == "deny")
            {
                return;
            }

            else
            {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null)
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                    return;
                }

                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("tpa-request")));
            }
        }
        else
        {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("not-player")));
        }
    }
}

