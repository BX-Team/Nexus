package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.SoundUtil;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public SpawnCommand() {
        super("spawn", "Teleports to spawn", "/spawn", "", "nexus.command.spawn");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("setspawn")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.setspawn")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            player.getWorld().setSpawnLocation(player.getLocation());
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("spawn-set")));
            SoundUtil.playSound(player, "setspawn");
        } else if (label.equalsIgnoreCase("spawn")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.spawn")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                player.teleport(player.getWorld().getSpawnLocation());
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("spawn-success")));
                SoundUtil.playSound(player, "spawn");
            } else if (args.length == 1) {
                if (!sender.hasPermission("nexus.command.spawn.other")) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                    return;
                }

                target.teleport(target.getWorld().getSpawnLocation());
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("spawn-success-sender").replace("%t", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("spawn-success-target").replace("%s", sender.getName())));
                SoundUtil.playSound(target, "spawn");
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("spawn-usage")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }

        return list;
    }
}
