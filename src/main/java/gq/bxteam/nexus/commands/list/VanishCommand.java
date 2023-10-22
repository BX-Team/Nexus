package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.TextUtils;
import gq.bxteam.nexus.utils.VanishUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public VanishCommand() {
        super("vanish", "Vanish command", "/vanish [player]", "/v", "nexus.command.vanish");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.vanish")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            if (VanishUtils.isPlayerVanished(player)) {
                VanishUtils.unvanishPlayer(player);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-unvanish")));
            } else {
                VanishUtils.vanishPlayer(player);
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-vanish")));
            }
        } else if (args.length == 1) {
            if (!sender.hasPermission("nexus.command.vanish")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                return;
            }

            if (VanishUtils.isPlayerVanished(target)) {
                VanishUtils.unvanishPlayer(target);
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-unvanish")));
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-unvanish-sender").replace("%t", target.getName())));
            } else {
                VanishUtils.vanishPlayer(target);
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-vanish")));
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-vanish-sender").replace("%t", target.getName())));
            }
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("vanish-usage")));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
                return list;
            }
        }

        return null;
    }
}
