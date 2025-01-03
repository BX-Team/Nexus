package space.bxteam.nexus.core.feature.ignore.database;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreRepository {
    CompletableFuture<Void> ignore(UUID sender, UUID target);

    CompletableFuture<Void> ignoreAll(UUID sender);

    CompletableFuture<Void> unIgnore(UUID sender, UUID target);

    CompletableFuture<Void> unIgnoreAll(UUID sender);

    CompletableFuture<Boolean> isIgnored(UUID sender, UUID target);
}
