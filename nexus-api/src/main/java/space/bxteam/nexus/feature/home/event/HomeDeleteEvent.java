package space.bxteam.nexus.feature.home.event;

import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import space.bxteam.nexus.feature.home.Home;

import java.util.UUID;

public class HomeDeleteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final UUID owner;
    @Getter
    private final Home home;
    private boolean cancelled;

    public HomeDeleteEvent(UUID owner, Home home) {
        super(false);
        this.owner = owner;
        this.home = home;
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
