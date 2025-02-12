package org.bxteam.nexus.core.feature.ignore;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bxteam.nexus.core.feature.ignore.database.IgnoreRepository;
import org.bxteam.nexus.feature.ignore.IgnoreService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IgnoreServiceImpl implements IgnoreService {
    private final IgnoreRepository ignoreRepository;

    @Override
    public CompletableFuture<Boolean> ignore(UUID requester, UUID target) {
        return this.ignoreRepository.ignore(requester, target).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> ignoreAll(UUID requester) {
        return this.ignoreRepository.ignoreAll(requester).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnore(UUID requester, UUID target) {
        return this.ignoreRepository.unIgnore(requester, target).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> unIgnoreAll(UUID requester) {
        return this.ignoreRepository.unIgnoreAll(requester).thenApply(unused -> true);
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(UUID requester, UUID target) {
        return this.ignoreRepository.isIgnored(requester, target);
    }
}
