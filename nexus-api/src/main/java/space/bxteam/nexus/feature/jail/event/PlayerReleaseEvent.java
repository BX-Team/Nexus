package space.bxteam.nexus.feature.jail.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Called when a player is released from jail.
 */
public class PlayerReleaseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID uuid;
    private boolean cancelled;

    public PlayerReleaseEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getPlayerUniqueId() {
        return this.uuid;
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
