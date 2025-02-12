package org.bxteam.nexus.feature.ignore.event;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * This event is called when a player unignores all players.
 */
@Accessors(fluent = false)
public class UnIgnoreAllEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final UUID requester;
    private boolean cancelled;

    public UnIgnoreAllEvent(UUID requester) {
        super(true);
        this.requester = requester;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
