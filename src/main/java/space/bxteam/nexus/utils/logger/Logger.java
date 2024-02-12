package space.bxteam.nexus.utils.logger;

import space.bxteam.nexus.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Logger {
    public static void log(String message, LogLevel level, boolean toAdmins) {
        if (message == null) return;

        String prefix = "";
        switch (level) {
            case ERROR -> prefix = "&8[&c&lERROR&r&8]";
            case WARNING -> prefix = "&8[&6&lWARNING&r&8]";
            case INFO -> prefix = "&8[&e&lINFO&r&8]";
            case DEBUG -> prefix = "&8[&b&lDEBUG&r&8]";
        }

        Bukkit.getConsoleSender().sendMessage(TextUtils.applyColor("&7[&6Nexus&7] " + prefix + " &f" + message));

        if (!toAdmins) return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("nexus.admin")) {
                player.sendMessage(ChatColor.GOLD + message);
            }
        }
    }

    public enum LogLevel { ERROR, WARNING, INFO, DEBUG }
}
