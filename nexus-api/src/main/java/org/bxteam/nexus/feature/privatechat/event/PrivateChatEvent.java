package org.bxteam.nexus.feature.privatechat.event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * This event is called when a player sends a private message to another player.
 */
@Getter
@Accessors(fluent = false)
public class PrivateChatEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID sender;
    private final UUID receiver;
    @Setter
    private String content;

    public PrivateChatEvent(UUID sender, UUID receiver, String content) {
        super(true);

        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
