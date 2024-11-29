package space.bxteam.nexus.feature.teleport.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Event called when a player is teleported.
 * <p>
 * Some notes:
 * <p>
 * 1) This event is called when the {@link space.bxteam.nexus.feature.teleport.TeleportService} teleports the player.
 * <p>
 * 2) Not working with vanilla teleportation.
 * <p>
 * 3) The event is cancellable and not async.
 */
public class TeleportEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private Location location;

    public TeleportEvent(Player player, Location location) {
        super(player);

        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
