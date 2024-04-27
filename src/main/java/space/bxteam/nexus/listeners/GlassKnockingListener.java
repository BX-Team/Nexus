package space.bxteam.nexus.listeners;

import space.bxteam.nexus.Nexus;
import space.bxteam.nexus.utils.SoundUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class GlassKnockingListener implements Listener {
    @EventHandler
    public void onGlassKnockingEvent(PlayerInteractEvent event) {
        if (!Nexus.getInstance().getConfigBoolean("player.glass-knocking.enable")) return;
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!player.isSneaking()
                || block == null
                || !(block.getType().toString().contains("GLASS"))) return;

        SoundUtil.playSound(player, block.getLocation(), "glass-knocking");
    }
}