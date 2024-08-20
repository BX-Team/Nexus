package space.bxteam.nexus.core.feature.warp;

import org.bukkit.Location;
import space.bxteam.nexus.feature.warp.Warp;
import space.bxteam.nexus.feature.warp.WarpService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class WarpServiceImpl implements WarpService {
    @Override
    public Warp createWarp(String name, Location location) {
        return null;
    }

    @Override
    public void removeWarp(String warp) {

    }

    @Override
    public boolean warpExists(String name) {
        return false;
    }

    @Override
    public Optional<Warp> findWarp(String name) {
        return Optional.empty();
    }

    @Override
    public Collection<String> getNamesOfWarps() {
        return List.of();
    }

    @Override
    public boolean hasWarps() {
        return false;
    }
}
