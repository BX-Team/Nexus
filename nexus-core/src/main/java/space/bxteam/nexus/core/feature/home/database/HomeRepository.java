package space.bxteam.nexus.core.feature.home.database;

import space.bxteam.nexus.feature.home.Home;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface HomeRepository {
    CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId);

    CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId, String homeName);

    CompletableFuture<Void> saveHome(Home home);

    CompletableFuture<Integer> deleteHome(UUID playerUniqueId);

    CompletableFuture<Integer> deleteHome(UUID playerUniqueId, String homeName);

    CompletableFuture<Set<Home>> getHomes();

    CompletableFuture<Set<Home>> getHomes(UUID playerUniqueId);
}
