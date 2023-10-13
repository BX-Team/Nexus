package gq.bxteam.nexus.listeners;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.commands.CommandBase;
import gq.bxteam.nexus.utils.SoundUtil;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void fullServerBypass(PlayerLoginEvent event) {
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            Player player = event.getPlayer();
            if (player.hasPermission("nexus.bypass.fullserver")) {
                event.allow();
                return;
            }

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, Nexus.getInstance().localeReader.getString("server-full"));
        }
    }

    public void sendJoinMessage(@NotNull Player player) {
        String message;
        if (player.hasPlayedBefore()) {
            message = Nexus.getInstance().localeReader.getString("join.message");
        } else {
            message = Nexus.getInstance().localeReader.getString("join.first-time.message");
        }
        message = message.replace("<player>", player.getName());

        if (Nexus.getInstance().getConfigBoolean("sound.join.enable")) {
            SoundUtil.playSound(player, player, "join");
        }
        CommandBase.sendGlobalMessage(TextUtils.applyColor(message));
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Nexus.getInstance().getConfigBoolean("features.join-message.enable")) {
            event.setJoinMessage(null);
            sendJoinMessage(player);
        }
    }
}
