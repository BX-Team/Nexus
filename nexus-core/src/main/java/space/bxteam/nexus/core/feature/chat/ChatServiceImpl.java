package space.bxteam.nexus.core.feature.chat;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import space.bxteam.nexus.core.configuration.plugin.PluginConfigurationProvider;
import space.bxteam.nexus.feature.chat.ChatService;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Singleton
public class ChatServiceImpl implements ChatService {
    private final PluginConfigurationProvider configurationProvider;
    private final Cache<UUID, Instant> slowModeCache;
    private boolean chatEnabled = true;

    @Inject
    public ChatServiceImpl(PluginConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        this.slowModeCache = Caffeine.newBuilder()
                .expireAfterWrite(configurationProvider.configuration().chat().slowMode().plus(Duration.ofSeconds(10)))
                .build();
    }

    @Override
    public void setCooldown(UUID player) {
        Duration slowMode = configurationProvider.configuration().chat().slowMode();

        this.slowModeCache.put(player, Instant.now().plus(slowMode));
    }

    @Override
    public boolean hasSlowedChat(UUID player) {
        return Instant.now().isBefore(this.slowModeCache.asMap().getOrDefault(player, Instant.MIN));
    }

    @Override
    public Duration getRemainingCoolDown(UUID player) {
        Instant endTime = this.slowModeCache.asMap().get(player);

        return endTime == null ? Duration.ZERO : Duration.between(Instant.now(), endTime);
    }

    @Override
    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    @Override
    public void setChatEnabled(boolean enabled) {
        this.chatEnabled = enabled;
    }
}
