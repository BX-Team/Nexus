package gq.bxteam.nexus.commands.list;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.SoundUtil;
import gq.bxteam.nexus.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeCommands extends CommandBase implements CommandExecutor, TabCompleter {
    public HomeCommands() {
        super("home", "Teleport to your home", "/home <name>", "", "nexus.command.home");
    }

    @SuppressWarnings({"DuplicatedCode", "deprecation"})
    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("sethome")) {
            if (args.length == 1 ) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.sethome")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().playerManager.getPlayerHome(player, args[0].toLowerCase()) != null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("sethome-exists")));
                    return;
                }
                Nexus.getInstance().playerManager.setPlayerHome(player, args[0].toLowerCase(), player.getLocation());
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("sethome-success").replace("%h", args[0].toLowerCase())));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("sethome-usage")));
            }
        } else if (label.equalsIgnoreCase("delhome")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.delhome")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().playerManager.getPlayerHome(player, args[0].toLowerCase()) == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-not-exists")));
                    return;
                }
                Nexus.getInstance().playerManager.deletePlayerHome(player, args[0].toLowerCase());
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("delhome-success").replace("%h", args[0].toLowerCase())));
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("delhome-usage")));
            }
        } else if (label.equalsIgnoreCase("home")) {
            if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.home")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().playerManager.getPlayerHome(player, args[0].toLowerCase()) == null) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-not-exists")));
                    return;
                }
                Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                player.teleport(Nexus.getInstance().playerManager.getPlayerHome(player, args[0].toLowerCase()));
                player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-success").replace("%h", args[0].toLowerCase())));
                SoundUtil.playSound(player, player, "home");
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-usage")));
            }
        } else if (label.equalsIgnoreCase("homes")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);

                if (!player.hasPermission("nexus.command.homes")) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().playerManager.getAllPlayerHomes(player).isEmpty()) {
                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("homes-empty")));
                    return;
                } else {
                    PaginatedGui gui = Gui.paginated()
                            .title(Component.text("Homes - " + player.getName()))
                            .rows(6)
                            .pageSize(45)
                            .disableAllInteractions()
                            .disableOtherActions()
                            .create();
                    gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> gui.previous()));
                    gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> gui.next()));

                    for (String home : Nexus.getInstance().playerManager.getAllPlayerHomes(player)) {
                        Location homeLocation = Nexus.getInstance().playerManager.getPlayerHome(player, home);
                        gui.addItem(ItemBuilder.from(Material.ENDER_PEARL).setName(home)
                                .setLore("X: " + homeLocation.getBlockX() + ", Y: " + homeLocation.getBlockY() + ", Z: " + homeLocation.getBlockZ())
                                .asGuiItem(event -> {
                                    Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                                    player.teleport(Nexus.getInstance().playerManager.getPlayerHome(player, home));
                                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-success").replace("%h", home)));
                                    SoundUtil.playSound(player, player, "home");
                                }));
                    }
                    gui.open(player);
                }
            } else if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("not-player")));
                    return;
                }

                Player player = (Player) (sender);
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                if (!sender.hasPermission("nexus.command.homes.other")) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                    return;
                }

                if (Nexus.getInstance().playerManager.getAllPlayerHomes(target).isEmpty()) {
                    sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("homes-empty-other").replace("%t", target.getName())));
                    return;
                } else {
                    PaginatedGui gui = Gui.paginated()
                            .title(Component.text("Homes - " + target.getName()))
                            .rows(6)
                            .pageSize(45)
                            .disableAllInteractions()
                            .disableOtherActions()
                            .create();
                    gui.setItem(6, 3, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event -> gui.previous()));
                    gui.setItem(6, 7, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event -> gui.next()));

                    for (String home : Nexus.getInstance().playerManager.getAllPlayerHomes(target)) {
                        Location homeLocation = Nexus.getInstance().playerManager.getPlayerHome(target, home);
                        gui.addItem(ItemBuilder.from(Material.ENDER_PEARL).setName(home)
                                .setLore("X: " + homeLocation.getBlockX() + ", Y: " + homeLocation.getBlockY() + ", Z: " + homeLocation.getBlockZ())
                                .asGuiItem(event -> {
                                    Nexus.getInstance().playerManager.setPlayerPreviousLocation(player, player.getLocation());
                                    player.teleport(Nexus.getInstance().playerManager.getPlayerHome(target, home));
                                    player.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("home-success").replace("%h", home)));
                                    SoundUtil.playSound(player, player, "home");
                                }));
                    }
                    gui.open(player);
                }
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("homes-usage")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("delhome")) {
            list.addAll(Nexus.getInstance().playerManager.getAllPlayerHomes(player));
            return list;
        } else if (label.equalsIgnoreCase("home")) {
            list.addAll(Nexus.getInstance().playerManager.getAllPlayerHomes(player));
            return list;
        } else if (label.equalsIgnoreCase("sethome")) {
            return null;
        } else if (label.equalsIgnoreCase("homes")) {
            if (args.length == 1) {
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    list.add(offlinePlayer.getName());
                }
                return list;
            }
        }

        return null;
    }
}
