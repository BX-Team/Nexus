package org.bxteam.nexus.core.feature.warp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;
import org.bxteam.nexus.core.feature.warp.database.WarpRepository;
import org.bxteam.nexus.feature.warp.Warp;
import org.bxteam.nexus.feature.warp.WarpService;

import java.util.*;

@Singleton
public class WarpServiceImpl implements WarpService {
    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;

    @Inject
    public WarpServiceImpl(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;

        warpRepository.getWarps().thenAcceptAsync(warps -> {
            for (Warp warp : warps) {
                this.warpMap.put(warp.name(), warp);
            }
        });
    }

    @Override
    public void createWarp(String name, Location location) {
        Warp warp = new WarpImpl(name, location);

        this.warpMap.put(name, warp);
        this.warpRepository.addWarp(warp);
    }

    @Override
    public void removeWarp(String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove);
    }

    @Override
    public boolean warpExists(String name) {
        return this.warpMap.containsKey(name);
    }

    @Override
    public Optional<Warp> getWarp(String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    @Override
    public Collection<String> getWarpNames() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }
}
