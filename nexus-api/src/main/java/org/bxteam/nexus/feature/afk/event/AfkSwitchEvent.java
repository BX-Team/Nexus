package org.bxteam.nexus.feature.afk.event;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bxteam.nexus.feature.afk.AfkPlayer;
import org.bxteam.nexus.feature.afk.AfkReason;

/**
 * This event is called when a player switches their AFK status.
 */
@Accessors(fluent = false)
public class AfkSwitchEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final AfkPlayer afkPlayer;
    @Getter
    private final AfkReason afkReason;
    @Getter
    private final boolean isAfk;
    private boolean cancelled;

    public AfkSwitchEvent(AfkPlayer afkPlayer, AfkReason afkReason, boolean isAfk) {
        super(false);
        this.afkPlayer = afkPlayer;
        this.afkReason = afkReason;
        this.isAfk = isAfk;
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
