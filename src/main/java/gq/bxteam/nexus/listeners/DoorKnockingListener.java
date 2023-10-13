package gq.bxteam.nexus.listeners;

import gq.bxteam.nexus.Nexus;
import gq.bxteam.nexus.utils.SoundUtil;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DoorKnockingListener implements Listener {
    @EventHandler
    public void onDoorKnockingEvent(PlayerInteractEvent event) {
        if (!Nexus.getInstance().getConfigBoolean("features.door-knocking.enable")) return;
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (!player.isSneaking()
                || block == null
                || !(block.getBlockData() instanceof Door)) return;

        SoundUtil.playSound(player, block.getLocation(), "door-knocking");
    }
}
