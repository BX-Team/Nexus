package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public FlyCommand() {
        super("fly", "Enable or disable fly mode", "/fly [player]", "", "nexus.command.fly");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.fly")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-disabled")));
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-enabled")));
            }
        } else if (args.length == 1) {
            if (!sender.hasPermission("nexus.command.fly.other")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                return;
            }

            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
                target.setFlying(false);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-other-disabled").replace("%t", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-target-disabled").replace("%p", sender.getName())));
            } else {
                target.setAllowFlight(true);
                target.setFlying(true);
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-other-enabled").replace("%t", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-target-enabled").replace("%p", sender.getName())));
            }
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("fly-usage")));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1 && playert.hasPermission("nexus.command.fly.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}
