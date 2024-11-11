package space.bxteam.nexus.feature.home;

import org.bukkit.Location;

import java.util.UUID;

public interface Home {
    String name();

    Location location();

    UUID owner();
}
