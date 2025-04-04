package org.bxteam.nexus.feature.ignore.event;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * This event is called when a player ignores another player.
 */
@Accessors(fluent = false)
public class IgnoreEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final UUID requester;
    @Getter
    private final UUID target;
    private boolean cancelled;

    public IgnoreEvent(UUID requester, UUID target) {
        super(true);
        this.requester = requester;
        this.target = target;
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
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
