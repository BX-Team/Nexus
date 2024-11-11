package space.bxteam.nexus.feature.home;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface HomeService {
    void createHome(UUID player, String name, Location location);

    void deleteHome(UUID player, String name);

    boolean hasHome(UUID player, String name);

    boolean hasHome(UUID player, Home home);

    Collection<Home> getHomes(UUID player);

    Optional<Home> getHome(UUID player, String name);

    int getAmountOfHomes(UUID player);

    int getHomeLimit(Player player);
}
