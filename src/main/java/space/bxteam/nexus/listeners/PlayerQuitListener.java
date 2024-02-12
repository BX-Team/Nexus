package space.bxteam.nexus.listeners;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.commands.CommandBase;
import space.bxteam.nexus.utils.SoundUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements Listener {
    public void sendQuitMessage(@NotNull Player player) {
        String message = Nexus.getInstance().localeReader.getString("quit.message").replace("<player>", player.getName());

        if (Nexus.getInstance().getConfigBoolean("sound.quit.enable")) {
            SoundUtil.playSound(player, player, "quit");
        }
        CommandBase.sendGlobalMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Nexus.getInstance().getConfigBoolean("player.quit-message.enable")) {
            event.setQuitMessage(null);
            sendQuitMessage(player);
        }
    }
}
