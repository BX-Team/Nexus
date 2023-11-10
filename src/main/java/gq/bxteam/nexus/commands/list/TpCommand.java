package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.SoundUtil;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TpCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public TpCommand()
    {
        super("tp", "", "/tp <from> <to>", "/teleport", "nexus.command.tp");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("teleport-usage")));
            return;
        }

        if (args.length > 3) {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("teleport-usage")));
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.command.tp")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("no-permission")));
                return;
            }

            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                    return;
                }

                if (target == player) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("teleport-self")));
                    return;
                }

                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-complete-1.3").replace("%t1", target.getName())));
                target.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-complete-1.4").replace("%t1", player.getName())));
                Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                player.teleport(target.getLocation());
                SoundUtil.playSound(player, "tp");

            } else if (args.length == 2) {
                Player target_from = Bukkit.getPlayer(args[0]);
                Player target_to = Bukkit.getPlayer(args[1]);

                if (!player.hasPermission("nexus.command.teleport.other")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("no-permission")));
                    return;
                }

                if (target_from == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                    return;
                }

                if (target_to == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("invalid-player")));
                    return;
                }

                if (target_from == target_to) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                            .getString("teleport-self")));
                    return;
                }

                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-complete-2.3").replace("%t1", target_from.getName()).replace("%t2", target_to.getName())));
                target_from.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-complete-1.1").replace("%t1", target_from.getName()).replace("%t2", sender.getName())));
                target_to.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-complete-1.2").replace("%t1", target_from.getName()).replace("%t2", sender.getName())));
                Nexus.getInstance().playerManager.setPlayerPreviousLocation(target_from, target_from.getLocation());
                target_from.teleport(target_to.getLocation());
                SoundUtil.playSound(target_from, "tp");

            }
        } else {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Player target_from = Bukkit.getPlayer(args[0]);
            Player target_to = Bukkit.getPlayer(args[1]);

            if (target_from == null) {
                console.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("invalid-player")));
                return;
            }

            if (target_to == null) {
                console.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("invalid-player")));
                return;
            }

            if (target_from == target_to) {
                console.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                        .getString("teleport-self")));
                return;
            }

            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("teleport-complete-2.3").replace("%t1", target_from.getName()).replace("%t2", target_to.getName())));
            target_from.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("teleport-complete-2.1").replace("%t", target_to.getName())));
            target_to.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader
                    .getString("teleport-complete-2.2").replace("%t", target_from.getName())));
            Nexus.getInstance().playerManager.setPlayerPreviousLocation(target_from, target_from.getLocation());
            target_from.teleport(target_to.getLocation());
            SoundUtil.playSound(target_from, "tp");
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
