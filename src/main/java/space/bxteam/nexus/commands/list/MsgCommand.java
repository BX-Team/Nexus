package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class MsgCommand extends CommandBase implements CommandExecutor
{
    public MsgCommand()
    {
        super("message", "Send private message to player", "/message <player> <message>\n/r <message>", "/msg\n/m\n/r\n/reply   ", "nexus.command.message");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (label.equalsIgnoreCase("r"))
        {
            if (sender instanceof Player) {

                Player player = (Player) (sender);
                    String str_target = Nexus.getInstance().playerManager.getLastRecipient(player);

                if (!player.hasPermission("nexus.command.message")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("no-permission")));
                    return;
                }

                if (str_target != null && args.length >= 1 && args[0] != null)
                {

                    Player target = Bukkit.getPlayer(str_target);

                    StringBuilder message = new StringBuilder();

                    if (target == null)
                    {
                        sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                                .getString("invalid-player"))); // Должно выводить если никто не писал ничего
                        return;
                    }

                    for (int i = 0; i < args.length; i++)
                    {
                        message.append(args[i]).append(" ");
                        sender.sendMessage(TextUtils.applyColor("test"));
                    }

                    String msg_template = TextUtils.applyColor("&7[§6" + sender.getName() + " → " + target.getName() + "§7]§f " + message);
                    target.sendMessage(msg_template);
                    sender.sendMessage(msg_template);

                }
                else
                {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("reply-usage")));
                    return;
                }
            }

            else
            {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("not-player")));
                return;
            }

        }
        else
        {

            if (args.length < 2) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("message-usage")));
                return;
            }

            if (sender instanceof Player) {
                Player player = (Player) (sender);
                Player target = Bukkit.getPlayer(args[0]);
                StringBuilder message = new StringBuilder();

                if (!player.hasPermission("nexus.command.message")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (target == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                    return;
                }

                if (target == player) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("message-self")));
                    return;
                }

                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }

                if (!message.isEmpty()) {
                    message.deleteCharAt(message.length() - 1);
                }

                String msg_template = TextUtils.applyColor("&7[§6" + sender.getName() + " → " + target.getName() + "§7]§f " + message);
                target.sendMessage(msg_template);
                sender.sendMessage(msg_template);
                Nexus.getInstance().playerManager.setLastRecipient(player, String.valueOf(target));
                Nexus.getInstance().playerManager.setLastRecipient(target, String.valueOf(player));
            }
            else
            {
                Player target = Bukkit.getPlayer(args[0]);
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                StringBuilder message = new StringBuilder();

                if (target == null) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                    return;
                }

                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }

                if (!message.isEmpty()) {
                    message.deleteCharAt(message.length() - 1);
                }

                String msg_template = TextUtils.applyColor("&7[§6Console → " + target.getName() + "§7]§f " + message);
                console.sendMessage(msg_template);
                target.sendMessage(msg_template);
            }
        }
    }
}
