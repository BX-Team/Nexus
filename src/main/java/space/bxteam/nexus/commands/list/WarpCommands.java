package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.SoundUtil;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommands extends CommandBase implements CommandExecutor, TabCompleter {
    public WarpCommands() {
        super("warp", "Teleports to warp location", "/warp <warp> [player]", "/w <warp>", "nexus.command.warp");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("setwarp")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.setwarp")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()) != null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("set-warp-exists")));
                    return;
                }
                Nexus.getInstance().warpManager.setWarp(args[0].toLowerCase(), player.getLocation());
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("set-warp-success").replace("%w", args[0].toLowerCase())));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("set-warp-usage")));
            }
        } else if (label.equalsIgnoreCase("delwarp")) {
            if (args.length == 1) {
                if (!sender.hasPermission("nexus.command.delwarp")) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()) == null) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-not-exists")));
                    return;
                }
                Nexus.getInstance().warpManager.removeWarp(args[0].toLowerCase());
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("del-warp-success").replace("%w", args[0].toLowerCase())));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("del-warp-usage")));
            }
        } else if (label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("w")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.warp")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()) == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-not-exists")));
                    return;
                }
                Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                player.teleport(Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()));
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-success").replace("%w", args[0].toLowerCase())));
                SoundUtil.playSound(player, player, "warp");
            } else if (args.length == 2) {
                if (!sender.hasPermission("nexus.command.warp.other")) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                    return;
                }

                if (Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()) == null) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-not-exists")));
                    return;
                }
                Nexus.getInstance().playerManager.setPlayerPreviousLocation(target, target.getLocation());
                target.teleport(Nexus.getInstance().warpManager.getWarp(args[0].toLowerCase()));
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-success-sender").replace("%w", args[0].toLowerCase()).replace("%t", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-success-target").replace("%w", args[0].toLowerCase()).replace("%s", sender.getName())));
                SoundUtil.playSound(target, target, "warp");
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("warp-usage")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<>();

        if (label.equalsIgnoreCase("delwarp")) {
            list.addAll(Nexus.getInstance().warpManager.getAllWarps());
            return list;
        } else if (label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("w")) {
            if (args.length == 1) {
                list.addAll(Nexus.getInstance().warpManager.getAllWarps());
                return list;
            } else if (args.length == 2 && playert.hasPermission("nexus.command.warp.other")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
                }
                return list;
            }
        }

        return null;
    }
}
