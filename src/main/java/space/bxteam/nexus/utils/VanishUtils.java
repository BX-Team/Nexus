package space.bxteam.nexus.utils;

import space.bxteam.nexus.Nexus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishUtils {
    public static void vanishPlayer(Player player) {
        Nexus.getInstance().playerManager.setVanishedState(player, true);
        updatePlayerVisibility(player);
    }

    public static void unvanishPlayer(Player player) {
        Nexus.getInstance().playerManager.setVanishedState(player, false);
        updatePlayerVisibility(player);
    }

    public static boolean isPlayerVanished(Player player) {
        return Nexus.getInstance().playerManager.getVanishedState(player);
    }

    public static void updatePlayerVisibility(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (isPlayerVanished(player)) {
                onlinePlayer.hidePlayer(Nexus.getInstance(), player);
                onlinePlayer.unlistPlayer(player);
            } else {
                onlinePlayer.showPlayer(Nexus.getInstance(), player);
                onlinePlayer.listPlayer(player);
            }
        }
    }
}