package space.bxteam.nexus.core.feature.jail.database;

import space.bxteam.nexus.feature.jail.JailPlayer;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface JailRepository {
    CompletableFuture<Optional<JailPlayer>> getPrisoner(UUID uuid);

    CompletableFuture<Set<JailPlayer>> getPrisoners();

    void deletePrisoner(UUID uuid);

    void editPrisoner(JailPlayer jailedPlayer);

    void deleteAllPrisoners();

    CompletableFuture<List<JailPlayer>> getAllPrisoners();

    void savePrisoner(JailPlayer jailedPlayer);
}
