package org.bxteam.nexus.feature.home.event;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bxteam.nexus.feature.home.Home;

import java.util.UUID;

/**
 * Event that is called when a player is teleported to their home.
 */
@Getter
@Accessors(fluent = false)
public class HomeTeleportEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final Home home;

    public HomeTeleportEvent(UUID playerUniqueId, Home home) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.home = home;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
