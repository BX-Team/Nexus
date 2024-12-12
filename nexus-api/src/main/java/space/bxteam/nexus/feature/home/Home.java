package space.bxteam.nexus.feature.home;

import org.bukkit.Location;

import java.util.UUID;

/**
 * Represents a home with the specified name, location and owner UUID.
 */
public interface Home {
    String name();

    Location location();

    UUID owner();
}
