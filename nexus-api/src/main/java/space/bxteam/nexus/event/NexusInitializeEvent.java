package space.bxteam.nexus.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when Nexus is initialized.
 */
public class NexusInitializeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}