package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.SoundUtil;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the /tphere, /tpup and /tpall commands.
 *
 * @since 0.1.0
 */
public class TpHereCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public TpHereCommand() {
        super("tphere", "Teleport player to you", "/tphere <player>", "", "nexus.command.tphere");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("tphere")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);
                Player target = Bukkit.getPlayer(args[0]);

                if (!player.hasPermission("nexus.command.tphere")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (target == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
                    return;
                }

                if (player == target) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tphere-self")));
                    return;
                }

                Nexus.getInstance().playerManager.setPlayerPreviousLocation(target, target.getLocation());
                target.teleport(player.getLocation());
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tphere-success").replace("%t", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tphere-success-target").replace("%p", player.getName())));
                SoundUtil.playSound(target, "tphere");
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tphere-usage")));
            }
        } else if (label.equalsIgnoreCase("tpup")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.tpup")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getWorld().getHighestBlockYAt(player.getLocation()) + 1, player.getLocation().getZ()));
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpup-success")));
                SoundUtil.playSound(player, "tpup");
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpup-usage")));
            }
        } else if (label.equalsIgnoreCase("tpall")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.tpall")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Bukkit.getOnlinePlayers().size() > 1) {
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        Nexus.getInstance().playerManager.setPlayerPreviousLocation(target, target.getLocation());
                        target.teleport(player.getLocation());
                        target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpall-success-target").replace("%p", player.getName())));
                        SoundUtil.playSound(target, "tpall");
                    }
                } else {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpall-no-players")));
                    return;
                }

                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpall-success")));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("tpall-usage")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (label.equalsIgnoreCase("tphere")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
                }
                return list;
            }
        } else if (label.equalsIgnoreCase("tpup")) {
            return null;
        } else if (label.equalsIgnoreCase("tpall")) {
            return null;
        }

        return null;
    }
}
