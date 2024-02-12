package space.bxteam.nexus.listeners;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.managers.PlayerManager;
import space.bxteam.nexus.utils.SoundUtil;
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
        CommandBase.sendGlobalMessage(message);
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Nexus.getInstance().getConfigBoolean("player.join-message.enable")) {
            event.setJoinMessage(null);
            sendJoinMessage(player);
        }

        PlayerManager playerManager = new PlayerManager(Nexus.getInstance());
        playerManager.savePlayerData(player, playerManager.getPlayerData(player));
    }
}
