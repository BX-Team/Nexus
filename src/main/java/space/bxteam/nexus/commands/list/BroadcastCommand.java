package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends CommandBase implements CommandExecutor {
    public BroadcastCommand() {
        super("broadcast", "Send message to all players", "/broadcast <message>", "/bc", "nexus.command.broadcast");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length >= 1) {
            if (!sender.hasPermission("nexus.command.broadcast")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                stringBuilder.append(arg).append(" ");
            }
            Bukkit.broadcastMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getString("broadcast-syntax").replace("<message>", stringBuilder.toString())));
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("broadcast-usage")));
        }
    }
}
