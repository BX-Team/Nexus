package gq.bxteam.nexus.commands.list;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.IBase;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class GamemodeCommand extends IBase implements CommandExecutor, TabCompleter {
    public GamemodeCommand() {
        super("gamemode", "Changes the player gamemode.", "/gamemode <gamemode> [player]", "/gm <gamemode> [player]\n/gms\n/gmc\n/gma\n/gmsp", "nexus.gamemode");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.error.not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.gamemode")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.error.no-permission")));
            }

            GameMode gamemode;
            if (label.equalsIgnoreCase("gms")) {
                gamemode = GameMode.SURVIVAL;
            } else if (label.equalsIgnoreCase("gmc")) {
                gamemode = GameMode.CREATIVE;
            } else if (label.equalsIgnoreCase("gma")) {
                gamemode = GameMode.ADVENTURE;
            } else if (label.equalsIgnoreCase("gmsp")) {
                gamemode = GameMode.SPECTATOR;
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.error")));
                return;
            }

            player.setGameMode(gamemode);
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.success").replace("%g", gamemode.toString().toLowerCase())));
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.error.not-player")));
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nexus.gamemode")) {
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.error.no-permission")));
            }

            GameMode gamemode;
            if (args[0].equalsIgnoreCase("0") || label.equalsIgnoreCase("gms")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1") || label.equalsIgnoreCase("gmc")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2") || label.equalsIgnoreCase("gma")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3") || label.equalsIgnoreCase("gmsp")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("survival")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("creative")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("adventure")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("spectator")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("s")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("c")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("a")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("sp")) {
                gamemode = GameMode.SPECTATOR;
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.error")));
                return;
            }

            player.setGameMode(gamemode);
            player.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.success").replace("%g", gamemode.toString().toLowerCase())));
        } else if (args.length == 2) {
            if (!sender.hasPermission("nexus.gamemode.other")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.error.no-permission")));
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            GameMode gameMode;
            if (args[0].equalsIgnoreCase("0")) {
                gameMode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1")) {
                gameMode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2")) {
                gameMode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3")) {
                gameMode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("survival")) {
                gameMode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("creative")) {
                gameMode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("adventure")) {
                gameMode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("spectator")) {
                gameMode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("s")) {
                gameMode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("c")) {
                gameMode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("a")) {
                gameMode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("sp")) {
                gameMode = GameMode.SPECTATOR;
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.error")));
                return;
            }

            target.setGameMode(gameMode);
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.other.success").replace("%t", target.getName()))
                    .replace("%g", gameMode.toString().toLowerCase()));
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().getConfigString("messages.prefix") + Nexus.getInstance().getConfigString("messages.gamemode.usage")));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1 && playert.hasPermission("nexus.gamemode")) {
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("survival");
            list.add("creative");
            list.add("adventure");
            list.add("spectator");
            list.add("s");
            list.add("c");
            list.add("a");
            list.add("sp");
        } else if (args.length == 2 && playert.hasPermission("nexus.gamemode.other")) {
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
