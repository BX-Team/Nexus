package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TimeCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public TimeCommand() {
        super("day", "Changes the world time", "/day\n/night\n/midnight\n/noon", "", "nexus.command.time");
    }
    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.time")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (label.equalsIgnoreCase("day")) {
                player.getLocation().getWorld().setTime(1000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "day").replace("%w", player.getWorld().getName())));
            } else if (label.equalsIgnoreCase("night")) {
                player.getLocation().getWorld().setTime(13000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "night").replace("%w", player.getWorld().getName())));
            } else if (label.equalsIgnoreCase("midnight")) {
                player.getLocation().getWorld().setTime(18000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "midnight").replace("%w", player.getWorld().getName())));
            } else if (label.equalsIgnoreCase("noon")) {
                player.getLocation().getWorld().setTime(6000);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "noon").replace("%w", player.getWorld().getName())));
            }
        } else if (args.length == 1) {
            World world = Bukkit.getWorld(args[0]);

            if (world == null) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-invalid")));
                return;
            }

            if (label.equalsIgnoreCase("day")) {
                world.setTime(1000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "day").replace("%w", world.getName())));
            } else if (label.equalsIgnoreCase("night")) {
                world.setTime(13000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "night").replace("%w", world.getName())));
            } else if (label.equalsIgnoreCase("midnight")) {
                world.setTime(18000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "midnight").replace("%w", world.getName())));
            } else if (label.equalsIgnoreCase("noon")) {
                world.setTime(6000);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-success").replace("%t", "noon").replace("%w", world.getName())));
            }
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("time-usage")));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> worldNames = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }

        return worldNames;
    }
}
