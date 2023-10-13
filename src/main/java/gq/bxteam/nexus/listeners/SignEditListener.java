package gq.bxteam.nexus.listeners;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEditListener implements Listener {
    @EventHandler
    public void onPlayerSignEdit(SignChangeEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String[] lines = event.getLines();
        if (!Nexus.getInstance().getConfigBoolean("features.sign-formatting.enable") ||
                !player.hasPermission("nexus.feature.sign-formatting")) return;

        for (int i = 0; i < lines.length; i++) {
            event.setLine(i, TextUtils.applyColor(lines[i]));
        }
    }
}
