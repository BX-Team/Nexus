package space.bxteam.nexus.feature.randomteleport.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that is called after a player is randomly teleported.
 */
public class RandomTeleportEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Location teleportLocation;

    public RandomTeleportEvent(Player player, Location teleportLocation) {
        super(false);

        this.player = player;
        this.teleportLocation = teleportLocation;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getTeleportLocation() {
        return this.teleportLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
