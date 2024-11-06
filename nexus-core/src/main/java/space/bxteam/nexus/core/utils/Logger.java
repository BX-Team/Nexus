package space.bxteam.nexus.core.utils;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Utility class for logging messages to the console with various log levels and color formatting.
 */
public final class Logger {
    private static final String PLUGIN_PREFIX = "&6[&eNexus&6] &7";
    @Setter
    private static boolean debugEnabled = false;

    private Logger() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Logs a message to the console with the specified log level.
     *
     * @param message the message to be logged
     * @param level   the level of the log (INFO, WARNING, ERROR, DEBUG)
     */
    public static void log(String message, LogLevel level) {
        if (message == null) return;

        String formattedMessage = PLUGIN_PREFIX + getPrefix(level) + " &f" + message;
        Bukkit.getConsoleSender().sendMessage(color(formattedMessage));
    }

    /**
     * Retrieves the prefix for each log level with color encoding.
     *
     * @param level the log level
     * @return the formatted prefix for the specified level
     */
    private static String getPrefix(LogLevel level) {
        return switch (level) {
            case ERROR -> "&8[&c&lERROR&r&8]";
            case WARNING -> "&8[&6&lWARNING&r&8]";
            case INFO -> "&8[&e&lINFO&r&8]";
            case DEBUG -> debugEnabled ? "&8[&b&lDEBUG&r&8]" : "";
        };
    }

    /**
     * Translates color codes from '&' to the Minecraft color format.
     *
     * @param message the message with color codes
     * @return the formatted message with Bukkit-compatible color codes
     */
    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Enum for log levels with associated color codes.
     */
    public enum LogLevel { ERROR, WARNING, INFO, DEBUG }
}
