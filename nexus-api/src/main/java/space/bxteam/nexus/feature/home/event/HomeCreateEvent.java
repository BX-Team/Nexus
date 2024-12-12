package space.bxteam.nexus.feature.home.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * Event that is called when a home is created.
 */
public class HomeCreateEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final UUID owner;
    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private Location location;
    private boolean cancelled;

    public HomeCreateEvent(UUID owner, String name, Location location) {
        super(false);
        this.owner = owner;
        this.name = name;
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
