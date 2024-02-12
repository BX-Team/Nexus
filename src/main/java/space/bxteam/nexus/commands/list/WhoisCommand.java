package space.bxteam.nexus.commands.list;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WhoisCommand extends CommandBase implements CommandExecutor, TabCompleter {
    public WhoisCommand() {
        super("whois", "Show information about player", "/whois <player>", "/info\n/playerinfo\n/who", "nexus.command.whois");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            if (!sender.hasPermission("nexus.command.whois")) {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("no-permission")));
                return;
            }

            Player player = Bukkit.getPlayer(args[0]);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            if (player != null) {
                List<String> languageMessage = Nexus.getInstance().localeReader.getStringList("whois-online");

                for (String message : languageMessage) {
                    message = message.replace("<player>", player.getName())
                            .replace("<uuid>", player.getUniqueId().toString())
                            .replace("<ip>", player.getAddress().getHostString())
                            .replace("<ping>", String.valueOf(player.getPing()))
                            .replace("<level>", String.valueOf(player.getLevel()))
                            .replace("<health>", String.valueOf(player.getHealth()))
                            .replace("<food>", String.valueOf(player.getFoodLevel()))
                            .replace("<gamemode>", player.getGameMode().toString())
                            .replace("<x>", String.valueOf(player.getLocation().getBlockX()))
                            .replace("<y>", String.valueOf(player.getLocation().getBlockY()))
                            .replace("<z>", String.valueOf(player.getLocation().getBlockZ()));

                    sender.sendMessage(TextUtils.applyColor(message));
                }
            } else if (offlinePlayer != null) {
                List<String> languageMessage = Nexus.getInstance().localeReader.getStringList("whois-offline");

                long lastOnline = offlinePlayer.getLastPlayed();
                Instant instant = Instant.ofEpochMilli(lastOnline);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                for (String message : languageMessage) {
                    message = message.replace("<player>", offlinePlayer.getName())
                            .replace("<uuid>", offlinePlayer.getUniqueId().toString())
                            .replace("<last_online>", dateTime.format(formatter));

                    sender.sendMessage(TextUtils.applyColor(message));
                }
            } else {
                sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("invalid-player")));
            }
        } else {
            sender.sendMessage(TextUtils.applyColor(Nexus.getInstance().localeReader.getPrefix() + Nexus.getInstance().localeReader.getString("whois-usage")));
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                list.add(player.getName());
            }
        }

        return list;
    }
}
