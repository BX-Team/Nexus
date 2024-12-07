package space.bxteam.nexus.feature.warp.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import space.bxteam.nexus.feature.warp.Warp;

/**
 * Event called when a player is teleported to a warp.
 */
public class WarpTeleportEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Warp warp;

    public WarpTeleportEvent(Player player, Warp warp) {
        super(false);

        this.player = player;
        this.warp = warp;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Warp getWarp() {
        return this.warp;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
