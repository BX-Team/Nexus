package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MessageCommand extends CommandBase implements CommandExecutor
{
    public MessageCommand() {
        super("message", "", "/message [player] [message]", "/msg\n/m", "nexus.command.message");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args)
    {

        if (args.length < 2)
        {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("message-usage")));
            return;
        }

        if (sender instanceof Player)
        {
            Player player = (Player) (sender);
            Player target = Bukkit.getPlayer(args[0]);
            StringBuilder message = new StringBuilder();

            if (!player.hasPermission("nexus.command.message")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (target == null)
            {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                return;
            }

            if (target == player)
            {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("message-self")));
                return;
            }

            for (int i = 1; i < args.length; i++)
            {
                message.append(args[i]).append(" ");
            }

            if (!message.isEmpty())
            {
                message.deleteCharAt(message.length() - 1);
            }

            String msg_template = TextUtils.applyColor("&7[§6" + sender.getName() + " → " + target.getName() + "§7]§f" + message);
            target.sendMessage(msg_template);
        }

        else
        {
            Player target = Bukkit.getPlayer(args[0]);
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            StringBuilder message = new StringBuilder();

            if (target == null)
            {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                return;
            }

            for (int i = 1; i < args.length; i++)
            {
                message.append(args[i]).append(" ");
            }

            if (!message.isEmpty())
            {
                message.deleteCharAt(message.length() - 1);
            }

            String msg_template = TextUtils.applyColor("&7[§6Console → " + target.getName() + "§7]§f " + message);
            console.sendMessage(msg_template);
            target.sendMessage(msg_template);


        }
    }
}
