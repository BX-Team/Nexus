package space.bxteam.nexus.core.utils;

import org.bukkit.Bukkit;

public class LogUtil {
    public static void log(String message, LogLevel level) {
        if (message == null) return;

        String prefix = "";
        switch (level) {
            case ERROR -> prefix = "&8[&c&lERROR&r&8]";
            case WARNING -> prefix = "&8[&6&lWARNING&r&8]";
            case INFO -> prefix = "&8[&e&lINFO&r&8]";
            case DEBUG -> prefix = "&8[&b&lDEBUG&r&8]";
        }

        Bukkit.getConsoleSender().sendMessage(TextUtils.applyColor("&6[&eNexus&6] &7" + prefix + " &f" + message));
    }

    public enum LogLevel { ERROR, WARNING, INFO, DEBUG }
}
