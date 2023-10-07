package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HealCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public HealCommand() {
        super("heal", "Fills up your hunger and hearts", "/heal [player]", "", "nexus.command.heal");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.heal")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
            }

            if (player.getHealth() != player.getMaxHealth() || player.getFoodLevel() != 20) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.sendMessage(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("heal-success"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return;
            }
            player.sendMessage(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("heal-error"));

        } else if (args.length == 1) {
            if (!sender.hasPermission("nexus.command.heal.other")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                return;
            }

            if (target.getHealth() != 20.0 || target.getFoodLevel() != 20) {
                target.setHealth(20.0);
                target.setFoodLevel(20);
                target.sendMessage(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("heal-other-success").replace("%p", sender instanceof Player ? sender.getName() : "Console"));
                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
            sender.sendMessage(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("heal-other-error").replace("%t", target.getName()));

        } else {
            sender.sendMessage(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().getConfigString("heal-usage"));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1 && playert.hasPermission("nexus.command.heal.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
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
