package space.bxteam.nexus.core.feature.warp;

import org.bukkit.Location;
import space.bxteam.nexus.feature.warp.Warp;

public class WarpImpl implements Warp {
    private final String name;
    private final Location location;

    public WarpImpl(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
