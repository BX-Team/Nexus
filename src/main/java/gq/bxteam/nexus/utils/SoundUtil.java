package gq.bxteam.nexus.utils;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.logger.Logger;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundUtil {
    private static String[] getSound(String command) {
        if (!Nexus.getInstance().getConfigBoolean("sound." + command + ".enable")) return null;

        String sound = Nexus.getInstance().getConfigString("sound." + command + ".type");

        return sound.split(":");
    }

    public static void playSound(@Nullable Player sender, @Nullable Player recipient, @NotNull String command) {
        if (recipient == null) return;

        String[] params = getSound(command);
        if (params == null) return;

        try {
            recipient.playSound(recipient.getLocation(), Sound.valueOf(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2]));
        } catch (IllegalArgumentException exception) {
            Logger.log("Incorrect sound " + params[0] + " for " + command + ".sound.type", Logger.LogLevel.ERROR, true);
            exception.printStackTrace();
        }
    }

    public static void playSound(@Nullable Location location, @NotNull String command) {
        if (location == null || location.getWorld() == null) return;

        String[] params = getSound(command);
        if (params == null) return;

        try {
            location.getWorld().playSound(location, Sound.valueOf(params[0]), Float.parseFloat(params[1]), Float.parseFloat(params[2]));
        } catch (IllegalArgumentException exception) {
            Logger.log("Incorrect sound " + params[0] + " for " + command + ".sound.type", Logger.LogLevel.ERROR, true);
            exception.printStackTrace();
        }
    }

    public static void playSound(@Nullable Player player, @NotNull String command) {
        playSound(player, player, command);
    }
}
