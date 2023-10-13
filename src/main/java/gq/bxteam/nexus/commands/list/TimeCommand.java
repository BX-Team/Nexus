package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand extends CommandBase implements CommandExecutor {
    public TimeCommand() {
        super("day", "Changes the world time", "/day\n/night\n/midnight\n/noon", "", "nexus.command.time");
    }
    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.time")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (label.equalsIgnoreCase("day")) {
                player.getLocation().getWorld().setTime(1000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "day")));
            } else if (label.equalsIgnoreCase("night")) {
                player.getLocation().getWorld().setTime(13000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "night")));
            } else if (label.equalsIgnoreCase("midnight")) {
                player.getLocation().getWorld().setTime(18000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "midnight")));
            } else if (label.equalsIgnoreCase("noon")) {
                player.getLocation().getWorld().setTime(6000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "noon")));
            }
        }

        else
        {
            if (args[0] == null)
            {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("time-usage")));
                return;
            }

            World world = Bukkit.getWorld(args[0]);

            if (label.equalsIgnoreCase("day")) {
                world.setTime(1000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "day")));
            } else if (label.equalsIgnoreCase("night")) {
                world.setTime(13000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "night")));
            } else if (label.equalsIgnoreCase("midnight")) {
                world.setTime(18000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "midnight")));
            } else if (label.equalsIgnoreCase("noon")) {
                world.setTime(6000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "noon")));
            }
        }
    }
}
