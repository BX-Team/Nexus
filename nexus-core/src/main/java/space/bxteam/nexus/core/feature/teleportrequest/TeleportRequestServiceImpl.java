package space.bxteam.nexus.core.feature.teleportrequest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.feature.teleportrequest.TeleportRequestService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class TeleportRequestServiceImpl implements TeleportRequestService {
    private final Cache<UUID, UUID> requests;

    @Inject
    public TeleportRequestServiceImpl(PluginConfigurationProvider configurationProvider) {
        this.requests = Caffeine.newBuilder()
                .expireAfterWrite(configurationProvider.configuration().teleportRequest().requestTimeout())
                .build();
    }

    @Override
    public void createRequest(UUID requester, UUID target) {
        this.requests.put(requester, target);
    }

    @Override
    public void removeRequest(UUID requester) {
        this.requests.asMap().remove(requester);
    }

    @Override
    public boolean hasRequest(UUID requester, UUID target) {
        return target.equals(this.requests.getIfPresent(requester));
    }

    @Override
    public List<UUID> findRequests(UUID target) {
        return this.requests.asMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(target))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}