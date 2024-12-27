package space.bxteam.nexus.core.feature.warp.database;

import space.bxteam.nexus.feature.warp.Warp;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface WarpRepository {
    void addWarp(Warp warp);

    void removeWarp(Warp warp);

    CompletableFuture<Optional<Warp>> getWarp(String name);

    CompletableFuture<List<Warp>> getWarps();
}
